package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.interceptors.repository;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.service.Base64EncodingService;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.interceptors.InterceptorsConfig;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.interceptors.entity.CreditCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackageClasses = InterceptorsConfig.class)
@Import({Base64EncodingService.class})
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// This class when run standalone is ok, test passes. But when I run all tests in this repo this test class fails 
// with bad assertions, so far I do not know why.
class CreditCardRepositoryTest {

    private static final String CREDIT_CARD_NUMBER = "123234456567";
    private static final String ENCODED_CREDIT_CARD_NUMBER = "MTIzMjM0NDU2NTY3";

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    // Also, interceptors are not very good at manipulating entity state, as every time an interceptor is doing some 
    // change to the entity, for example in below first test, the credit card number is encoded, hibernate executes 
    // the update query for entity that is modified by an interceptor. So if you look closely at the logs from first 
    // test, there are 1 insert and 2 updates. Hibernate Listeners can do better.  

    @Test
    public void shouldSaveCreditCardWithEncodedCardNumber() {
        CreditCard creditCard = new CreditCard("123234456567", "486", "04/2030");
        CreditCard saved = creditCardRepository.saveAndFlush(creditCard);

        Map<String, Object> dbRow = jdbcTemplate.queryForMap("SELECT * FROM credit_card WHERE id = ?", saved.getId());
        String encodedCreditCardNumberStoredInDb = dbRow.get("credit_card_number").toString();

        CreditCard fetched = creditCardRepository.getById(saved.getId());

        assertThat(fetched.getId()).isNotNull();
        assertThat(fetched.getCreditCardNumber()).isEqualTo(CREDIT_CARD_NUMBER);
        assertThat(fetched.getExpirationDate()).isEqualTo("04/2030");
        assertThat(fetched.getCvv()).isEqualTo("486");
        assertThat(encodedCreditCardNumberStoredInDb).isEqualTo(ENCODED_CREDIT_CARD_NUMBER);
    }

    @Test
    @Sql(statements = "INSERT INTO credit_card (id, credit_card_number, cvv, expiration_date)" +
            "VALUES (777, 'MTIzMjM0NDU2NTY3', '678', '04/2030')")
    void shouldFetchCreditCardWithDecodedCardNumber() {
        CreditCard creditCard = creditCardRepository.getById(777L);

        assertThat(creditCard.getId()).isNotNull();
        assertThat(creditCard.getCreditCardNumber()).isEqualTo(CREDIT_CARD_NUMBER);
        assertThat(creditCard.getExpirationDate()).isEqualTo("04/2030");
        assertThat(creditCard.getCvv()).isEqualTo("678");
    }

}
