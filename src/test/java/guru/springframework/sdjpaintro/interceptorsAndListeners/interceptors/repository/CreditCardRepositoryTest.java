package guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.repository;

import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.service.Base64EncodingService;
import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.template.Decoder;
import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.template.Encoder;
import guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.InterceptorsConfig;
import guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.entity.CreditCard;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackageClasses = InterceptorsConfig.class)
@Import({Decoder.class, Encoder.class, Base64EncodingService.class})
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

// This class when run standalone is ok, test passes. But when I run all tests in this repo this test class fails 
// with bad assertions, so far I do not know why.

class CreditCardRepositoryTest {

    private static final String CREDIT_CARD_NUMBER = "123234456567";
    private static Long cardId = null;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // I implemented 2 separate ordered unit test for the purpose of saving the credit card in one hibernate session 
    // and fetching this credit card in another hibernate session. This way I am sure that when I fetch the credit 
    // card in second test it is fetched from db, not from hibernate session as it would when everything happened in 
    // one @Transactional test.

    // Also, interceptors are not very good at manipulating entity state, as every time an interceptor is doing some 
    // change to the entity, for example in below first test, the credit card number is encoded, hibernate executes 
    // the update query for entity that is modified by an interceptor. So if you look closely at the logs from first 
    // test, there are 1 insert and 2 updates. Hibernate Listeners can do better.  
    @Test
    @Order(1)
    @Commit
    public void shouldSaveCreditCardWithEncodedCardNumber() {
        CreditCard creditCard = new CreditCard(CREDIT_CARD_NUMBER, "486", "04/2030");

        CreditCard saved = creditCardRepository.saveAndFlush(creditCard);

        Map<String, Object> dbRow = jdbcTemplate.queryForMap("SELECT * FROM credit_card WHERE id = ?", saved.getId());
        String creditCardNumberStoredInDb = dbRow.get("credit_card_number").toString();

        String encodedCardNumber =
                Base64.getEncoder().encodeToString(CREDIT_CARD_NUMBER.getBytes(StandardCharsets.UTF_8));

        CreditCard fetched = creditCardRepository.getById(saved.getId());

        cardId = fetched.getId();

        assertThat(creditCardNumberStoredInDb).isEqualTo(encodedCardNumber);
        assertThat(fetched.getId()).isNotNull();
        assertThat(fetched.getCreditCardNumber()).isEqualTo(CREDIT_CARD_NUMBER);
        assertThat(fetched.getExpirationDate()).isEqualTo("04/2030");
        assertThat(fetched.getCvv()).isEqualTo("486");

    }

    @Test
    @Order(2)
    void shouldFetchCreditCardWithDecodedCardNumber() {
        CreditCard creditCard = creditCardRepository.getById(cardId);

        assertThat(creditCard.getId()).isNotNull();
        assertThat(creditCard.getCreditCardNumber()).isEqualTo(CREDIT_CARD_NUMBER);
        assertThat(creditCard.getExpirationDate()).isEqualTo("04/2030");
        assertThat(creditCard.getCvv()).isEqualTo("486");
    }

}
