package guru.springframework.sdjpaintro.hibernateJavaMappings.service;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.*;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.CustomerRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.ProductRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({"local"})
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Transactional
    void saveOrderHeader() {
        Address billingAddress = createBillingAddress();
        Address shippingAddress = createShippingAddress();

        Customer customer = customerRepository.save(new Customer("name", shippingAddress, "phone", "email"));

        Product product = productRepository.getByDescription("PRODUCT1");
        List<ImmutablePair<Long, Integer>> productQuantityList = List.of(ImmutablePair.of(product.getId(), 5));

        OrderHeader saved = orderService.saveOrderHeader(
                customer.getId(),
                billingAddress,
                shippingAddress,
                productQuantityList);

        OrderHeader fetched = orderService.fetchOrderHeaderById(saved.getId());
        //order header
        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isNotNull();
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
                .withFailMessage("All order line products should have descriptions.")
                .allMatch(ol -> ol.getProduct().getId() != null);
        assertThat(fetched.getOrderLines())
                .anyMatch(ol -> ol.getProduct().getDescription().equals("PRODUCT1")
                        && ol.getProduct().getProductStatus().equals(ProductStatus.NEW));
        //category
        assertThat(fetched.getOrderLines().stream()
                .map(o -> o.getProduct().getCategories())
                .flatMap(Collection::stream)
                .anyMatch(c -> c.getDescription().equals("CAT1") || c.getDescription().equals("CAT2")))
                .withFailMessage("Products should have correct categories")
                .isTrue();
        //customer
        assertThat(fetched.getCustomer().getName()).isEqualTo("name");
        assertThat(fetched.getCustomer().getEmail()).isEqualTo("email");
    }

    private static Address createShippingAddress() {
        return new Address("shipping address", "shipping city", "shipping state", "shipping zip code");
    }

    private static Address createBillingAddress() {
        return new Address("billing address", "billing city", "billing state", "billing zip code");
    }

}
