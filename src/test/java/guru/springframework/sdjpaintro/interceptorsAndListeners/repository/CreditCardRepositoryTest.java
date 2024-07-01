package guru.springframework.sdjpaintro.interceptorsAndListeners.repository;

import guru.springframework.sdjpaintro.interceptorsAndListeners.entity.CreditCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditCardRepositoryTest {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Test
    @Transactional
    void restSaveAndStoreCreditCard() {
        CreditCard creditCard = new CreditCard("123234456567", "486", "04/2030");

        CreditCard saved = creditCardRepository.saveAndFlush(creditCard);

        CreditCard fetchedById = creditCardRepository.getById(saved.getId());

        assertThat(fetchedById.getId()).isNotNull();
        assertThat(fetchedById.getCreditCardNumber()).isEqualTo("123234456567");
        assertThat(fetchedById.getExpirationDate()).isEqualTo("04/2030");
        assertThat(fetchedById.getCvv()).isEqualTo("486");
    }

}
