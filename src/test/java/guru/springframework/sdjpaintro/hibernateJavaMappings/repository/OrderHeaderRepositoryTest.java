package guru.springframework.sdjpaintro.hibernateJavaMappings.repository;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.Address;
import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.OrderHeader;
import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

    @Autowired
    private OrderHeaderRepository repository;

    @Test
    void shouldSaveOrderHeader() {
        Address billingAddress = new Address("billing address", "billing city", "billing state", "billing zip code");
        Address shippingAddress = new Address("shipping address", "shipping city", "shipping state", "shipping zip code");
        OrderHeader orderHeader = new OrderHeader("customer", shippingAddress, billingAddress, OrderStatus.NEW);

        OrderHeader saved = repository.save(orderHeader);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedDate()).isNotNull();
        assertThat(saved.getCreatedDate()).isBefore(Instant.now());
        assertThat(saved.getModifiedDate()).isNotNull();
        assertThat(saved.getModifiedDate()).isBefore(Instant.now());
        assertThat(saved.getModifiedDate()).isEqualTo(saved.getCreatedDate());
    }

    @Test
    void shouldModifyOrderHeader() throws Exception {
        Address billingAddress = new Address("billing address", "billing city", "billing state", "billing zip code");
        Address shippingAddress = new Address("shipping address", "shipping city", "shipping state", "shipping zip code");
        OrderHeader orderHeader = new OrderHeader("customer", shippingAddress, billingAddress, OrderStatus.NEW);

        OrderHeader saved = repository.save(orderHeader);

        saved.setCustomer("new Customer");

        Thread.sleep(3000L);

        OrderHeader updated = repository.save(saved);

        OrderHeader fetched = repository.findById(updated.getId()).get();

        assertThat(fetched.getId()).isEqualTo(saved.getId());
        assertThat(fetched.getCustomer()).isEqualTo("new Customer");
        // todo investigate why below assertion is failing
        assertThat(fetched.getCreatedDate()).isBefore(fetched.getModifiedDate());
    }

}
