package guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.repository;

import guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.InterceptorsConfig;
import guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.entity.CreditCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackageClasses = InterceptorsConfig.class)
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditCardRepositoryTest {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //make this test 2 separate tests with ordering

    @Test
    void restSaveAndStoreCreditCard() {
        CreditCard creditCard = new CreditCard("123234456567", "486", "04/2030");

        // Flushing here as in below lines we are using jdbcTemplate to fetch raw 'creditCardNumber' value that is 
        // encoded by hibernate stored id db.
        CreditCard saved = creditCardRepository.saveAndFlush(creditCard);

        Map<String, Object> dbRow = jdbcTemplate.queryForMap("SELECT * FROM credit_card WHERE id = ?", saved.getId());
        String creditCardNumberStoredInDb = dbRow.get("credit_card_number").toString();

        CreditCard fetchedById = creditCardRepository.getById(saved.getId());

        assertThat(fetchedById.getId()).isNotNull();
        assertThat(creditCardNumberStoredInDb).isNotEqualTo("123234456567");
        assertThat(fetchedById.getCreditCardNumber()).isEqualTo("123234456567");
        assertThat(fetchedById.getExpirationDate()).isEqualTo("04/2030");
        assertThat(fetchedById.getCvv()).isEqualTo("486");
    }

}