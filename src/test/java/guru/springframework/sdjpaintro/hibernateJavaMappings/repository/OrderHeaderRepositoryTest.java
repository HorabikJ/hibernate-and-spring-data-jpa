package guru.springframework.sdjpaintro.hibernateJavaMappings.repository;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private OrderApprovalRepository orderApprovalRepository;

    private Customer customer;

    @BeforeEach
    public void saveCustomer() {
        Address address = new Address("shipping address", "shipping city", "shipping state", "shipping zip code");
        customer = customerRepository.save(new Customer("name", address, "phone", "email"));
    }

    @Test
    void shouldSaveOrderWithOrderLine() {
        // CAT1 and PRODUCT1 are inserted into db by flyway script
        Product product = productRepository.getByDescription("PRODUCT1");
        Address billingAddress = new Address("billing address", "billing city", "billing state", "billing zip code");
        Address shippingAddress = new Address("shipping address", "shipping city", "shipping state", "shipping zip code");
        OrderLine orderLine = new OrderLine(1, product);
        OrderHeader orderHeader = new OrderHeader(
                customer,
                shippingAddress,
                billingAddress,
                OrderStatus.NEW,
                "approver name");
        orderHeader.associateOrderLine(orderLine);

        OrderHeader saved = orderHeaderRepository.save(orderHeader);
        orderHeaderRepository.flush();
        //order header
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getOrderLines()).size().isEqualTo(1);
        assertThat(saved.getOrderLines())
                .withFailMessage("All order lines should have non null ids.")
                .allMatch(ol -> ol.getId() != null);

        OrderHeader fetchedOrderHeader = orderHeaderRepository.getById(saved.getId());
        //order line
        assertThat(fetchedOrderHeader).isNotNull();
        assertThat(fetchedOrderHeader.getId()).isNotNull();
        assertThat(fetchedOrderHeader.getOrderLines()).size().isEqualTo(1);
        assertThat(saved.getOrderLines())
                .withFailMessage("All order lines should have non null ids.")
                .allMatch(ol -> ol.getId() != null);
        //product
        assertThat(fetchedOrderHeader.getOrderLines())
                .withFailMessage("All order line products should have non null ids.")
                .allMatch(ol -> ol.getProduct().getId() != null);
        assertThat(fetchedOrderHeader.getOrderLines())
                .withFailMessage("All order line products should have descriptions.")
                .anyMatch(ol -> ol.getProduct().getDescription().equals("PRODUCT1")
                        && ol.getProduct().getProductStatus().equals(ProductStatus.NEW));
        //category
        assertThat(fetchedOrderHeader.getOrderLines().stream()
                .map(o -> o.getProduct().getCategories())
                .flatMap(Collection::stream)
                .anyMatch(c -> c.getDescription().equals("CAT1") || c.getDescription().equals("CAT2")))
                .withFailMessage("Products should have correct categories")
                .isTrue();
        //order approval
        assertThat(fetchedOrderHeader.getOrderApproval().getId()).isNotNull();
        assertThat(fetchedOrderHeader.getOrderApproval().getApprovedBy()).isEqualTo("approver name");
        assertThat(fetchedOrderHeader.getOrderApproval().getOrderHeader()).isNotNull();
        assertThat(fetchedOrderHeader.getOrderApproval().getOrderHeader().getId()).isEqualTo(fetchedOrderHeader.getId());
    }

    @Test
    void shouldDeleteOrderHeaderAndCascadedEntitiesAsWell() {
        // CAT1 and PRODUCT1 are inserted into db by flyway script
        Product product = productRepository.getByDescription("PRODUCT1");
        Address billingAddress = new Address("billing address", "billing city", "billing state", "billing zip code");
        Address shippingAddress = new Address("shipping address", "shipping city", "shipping state", "shipping zip code");
        OrderLine orderLine = new OrderLine(1, product);
        OrderHeader orderHeader = new OrderHeader(
                customer,
                shippingAddress,
                billingAddress,
                OrderStatus.NEW,
                "approver name");
        orderHeader.associateOrderLine(orderLine);

        OrderHeader saved = orderHeaderRepository.saveAndFlush(orderHeader);
        orderHeaderRepository.flush();

        orderHeaderRepository.deleteById(saved.getId());
        orderHeaderRepository.flush();

        //order header
        Optional<OrderHeader> deletedOrderHeaderOptional = orderHeaderRepository.findById(saved.getId());
        assertThat(deletedOrderHeaderOptional).isEmpty();

        //order lines
        Set<Long> deletedOrderLinesIds = saved.getOrderLines().stream().map(BaseEntity::getId).collect(Collectors.toSet());
        assertThat(orderLineRepository.findAllById(deletedOrderLinesIds)).isEmpty();

        //order approval
        assertThat(orderApprovalRepository.findById(saved.getOrderApproval().getId())).isEmpty();
    }

    @Test
    void shouldSaveOrderHeader() {
        Address billingAddress = new Address("billing address", "billing city", "billing state", "billing zip code");
        Address shippingAddress = new Address("shipping address", "shipping city", "shipping state", "shipping zip code");
        OrderHeader orderHeader = new OrderHeader(customer, shippingAddress, billingAddress, OrderStatus.NEW, "approver name");
        
        
        OrderHeader saved = orderHeaderRepository.save(orderHeader);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedDate()).isNotNull();
        assertThat(saved.getCreatedDate()).isBefore(Instant.now());
        assertThat(saved.getModifiedDate()).isNotNull();
        assertThat(saved.getModifiedDate()).isBefore(Instant.now());
        //customer
        assertThat(saved.getCustomer().getId()).isNotNull();
        //order approval
        assertThat(saved.getOrderApproval().getId()).isNotNull();
        assertThat(saved.getOrderApproval().getApprovedBy()).isEqualTo("approver name");
        assertThat(saved.getOrderApproval().getOrderHeader()).isNotNull();
        assertThat(saved.getOrderApproval().getOrderHeader().getId()).isEqualTo(saved.getId());
        //order lines
        assertThat(saved.getOrderLines()).allMatch(ol -> ol.getId() != null);
    }

    @Test
    void shouldModifyOrderHeader() throws Exception {
        Address billingAddress = new Address("billing address", "billing city", "billing state", "billing zip code");
        Address shippingAddress = new Address("shipping address", "shipping city", "shipping state", "shipping zip code");
        OrderHeader orderHeader = new OrderHeader(customer, shippingAddress, billingAddress, OrderStatus.NEW, "approver name");

        OrderHeader saved = orderHeaderRepository.save(orderHeader);
        orderHeaderRepository.flush();
        saved.setOrderStatus(OrderStatus.COMPLETE);

        Thread.sleep(500L);

        OrderHeader updated = orderHeaderRepository.save(saved);
        orderHeaderRepository.flush();
        OrderHeader fetched = orderHeaderRepository.findById(updated.getId()).get();

        assertThat(fetched.getId()).isEqualTo(saved.getId());
        assertThat(fetched.getCustomer().getName()).isEqualTo("name");
        // When this test is @Transactional it fails - todo investigate why -> we need to flush() after each save, then 
        // correct update timestamps are set in entities in hibernate session. Without flushing, OrderHeader entity 
        // is first created and then modified modified in hibernate session only, not in db. We save and modify the 
        // entity in hibernate session, not in db layer, thus there is only one insert from hibernate session to db 
        // and because of that update and creation timestamps are the same. 
        assertThat(fetched.getCreatedDate()).isBefore(fetched.getModifiedDate());
    }

}
