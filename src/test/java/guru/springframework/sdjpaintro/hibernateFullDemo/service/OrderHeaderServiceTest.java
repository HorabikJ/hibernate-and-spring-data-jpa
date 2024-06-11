package guru.springframework.sdjpaintro.hibernateFullDemo.service;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.*;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.CustomerRepository;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.OrderApprovalRepository;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.OrderLineRepository;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.ProductRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles({"local"})
class OrderHeaderServiceTest {

    // It is always better to insert data to db via executing some sql script so before all test execution db is 
    // loaded with test data. Loading test data to db inside tests is not good practice, but for the sake of 
    // simplicity we do this here. We can load test data to db by using @Sql annotation on a test method.

    @Autowired
    private OrderHeaderService orderService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private OrderApprovalRepository orderApprovalRepository;

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
                productQuantityList,
                "approver name");

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
        //order approval
        assertThat(fetched.getOrderApproval().getId()).isNotNull();
        assertThat(fetched.getOrderApproval().getApprovedBy()).isEqualTo("approver name");
    }

    @Test
    @Transactional
    public void deleteOrderHeaderAndCascadedEntities() {
        Address billingAddress = createBillingAddress();
        Address shippingAddress = createShippingAddress();

        Customer customer = customerRepository.save(new Customer("name", shippingAddress, "phone", "email"));

        Product product = productRepository.getByDescription("PRODUCT1");
        List<ImmutablePair<Long, Integer>> productQuantityList = List.of(ImmutablePair.of(product.getId(), 5));

        OrderHeader savedOrderHeader = orderService.saveOrderHeader(
                customer.getId(),
                billingAddress,
                shippingAddress,
                productQuantityList,
                "approver name");

        orderService.deleteOrderHeader(savedOrderHeader.getId());

        //order header
        assertThatThrownBy(() -> orderService.fetchOrderHeaderById(savedOrderHeader.getId()))
                .isInstanceOf(JpaObjectRetrievalFailureException.class);

        //order lines
        Set<Long> deletedOrderLinesIds = savedOrderHeader.getOrderLines().stream().map(BaseEntity::getId).collect(Collectors.toSet());
        assertThat(orderLineRepository.findAllById(deletedOrderLinesIds)).isEmpty();

        //order approval
        assertThat(orderApprovalRepository.findById(savedOrderHeader.getOrderApproval().getId())).isEmpty();

    }

    private static Address createShippingAddress() {
        return new Address("shipping address", "shipping city", "shipping state", "shipping zip code");
    }

    private static Address createBillingAddress() {
        return new Address("billing address", "billing city", "billing state", "billing zip code");
    }

}
