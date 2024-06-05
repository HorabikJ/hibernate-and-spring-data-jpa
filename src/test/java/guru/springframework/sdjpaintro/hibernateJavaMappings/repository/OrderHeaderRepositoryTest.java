package guru.springframework.sdjpaintro.hibernateJavaMappings.repository;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.Address;
import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.OrderHeader;
import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.OrderLine;
import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.OrderStatus;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
class OrderHeaderRepositoryTest {

    @Autowired
    private OrderHeaderRepository repository;

    @Test
    void shouldSaveOrderWithLine() {
        Address billingAddress = new Address("billing address", "billing city", "billing state", "billing zip code");
        Address shippingAddress = new Address("shipping address", "shipping city", "shipping state", "shipping zip code");

        OrderLine orderLine = new OrderLine(1);
        OrderHeader orderHeader = new OrderHeader(
                "customer",
                shippingAddress,
                billingAddress,
                OrderStatus.NEW,
                Set.of(orderLine));
        orderLine.setOrderHeader(orderHeader);

        OrderHeader saved = repository.save(orderHeader);
        repository.flush();

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getOrderLines()).size().isEqualTo(1);
        assertThat(saved.getOrderLines())
                .withFailMessage("All order lines should have non null ids.")
                .allMatch(ol -> ol.getId() != null);

        OrderHeader fetched = repository.getById(saved.getId());

        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isNotNull();
        assertThat(fetched.getOrderLines()).size().isEqualTo(1);
        assertThat(saved.getOrderLines())
                .withFailMessage("All order lines should have non null ids.")
                .allMatch(ol -> ol.getId() != null);
    }

    @Test
    void shouldSaveOrderHeader() {
        Address billingAddress = new Address("billing address", "billing city", "billing state", "billing zip code");
        Address shippingAddress = new Address("shipping address", "shipping city", "shipping state", "shipping zip code");
        OrderHeader orderHeader = new OrderHeader("customer", shippingAddress, billingAddress, OrderStatus.NEW,
                Sets.newHashSet());

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
        OrderHeader orderHeader = new OrderHeader("customer", shippingAddress, billingAddress, OrderStatus.NEW,
                Sets.newHashSet());

        OrderHeader saved = repository.save(orderHeader);

        saved.setCustomer("new Customer");

        Thread.sleep(3000L);

        OrderHeader updated = repository.save(saved);

        OrderHeader fetched = repository.findById(updated.getId()).get();

        assertThat(fetched.getId()).isEqualTo(saved.getId());
        assertThat(fetched.getCustomer()).isEqualTo("new Customer");
        // when this test is @Transactional it fails - todo investigate why
        assertThat(fetched.getCreatedDate()).isBefore(fetched.getModifiedDate());
    }

}
