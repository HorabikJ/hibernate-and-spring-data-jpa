package guru.springframework.sdjpaintro.hibernateJavaMappings.service;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.*;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({"local"})
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void saveProduct() {
        Product radiusPump = new Product("radius pump", ProductStatus.NEW);
        product = productRepository.saveAndFlush(radiusPump);
    }

    @Test
    @Transactional
    void saveOrderHeader() {
        Address billingAddress = createBillingAddress();
        Address shippingAddress = createShippingAddress();
        OrderLine orderLine = new OrderLine(5, product);

        OrderHeader saved = orderService.saveOrderHeader("customer",
                billingAddress,
                shippingAddress,
                Set.of(orderLine));

        OrderHeader fetched = orderService.fetchOrderHeaderById(saved.getId());
        //order header
        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isNotNull();
        assertThat(fetched.getCustomer()).isEqualTo("customer");
        assertThat(fetched.getOrderStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(fetched.getBillingAddress().getAddress()).isEqualTo("billing address");
        assertThat(fetched.getShippingAddress().getAddress()).isEqualTo("shipping address");
        //order line
        assertThat(fetched.getOrderLines()).size().isEqualTo(1);
        assertThat(fetched.getOrderLines())
                .withFailMessage("All order lines should have non null ids.")
                .allMatch(ol -> ol.getId() != null);
        //product
        assertThat(fetched.getOrderLines())
                .withFailMessage("All order line products should have non null ids.")
                .allMatch(ol -> ol.getProduct().getId() != null);
        assertThat(fetched.getOrderLines())
                .withFailMessage("All order line products should have non null ids.")
                .anyMatch(ol -> ol.getProduct().getDescription().equals("radius pump")
                        && ol.getProduct().getProductStatus().equals(ProductStatus.NEW));
    }

    private static Address createShippingAddress() {
        return new Address("shipping address", "shipping city", "shipping state", "shipping zip code");
    }

    private static Address createBillingAddress() {
        return new Address("billing address", "billing city", "billing state", "billing zip code");
    }

}
