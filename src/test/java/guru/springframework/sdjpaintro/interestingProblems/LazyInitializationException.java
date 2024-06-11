package guru.springframework.sdjpaintro.interestingProblems;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.*;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.CustomerRepository;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.OrderHeaderRepository;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("local")
@SpringBootTest
@Slf4j
public class LazyInitializationException {

    private static final String CUSTOMER_NAME = "CustomerForLazyInitializationError";

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Test
    @Commit
    @Transactional
    @Order(1)
    // To run this test on fresh db simply drop all tables in db using MySQL Workbench, then start the 
    // SdjpaIntroApplication with the "local" and "clean" profiles and run this test.
    public void setupTestData() {
        // Products inserted via flyway script, I am using those because when I tried to insert products and 
        // categories inside of the test it did not work. 
        // It is always better to insert data to db via executing some sql script so before all test execution db is 
        // loaded with test data. Loading test data to db inside tests is not good practice, but for the sake of 
        // simplicity we do this here. We can load test data to db by using @Sql annotation on a test method.
        Product product1 = productRepository.getByDescription("PRODUCT1");
        Product product2 = productRepository.getByDescription("PRODUCT2");
        Product product3 = productRepository.getByDescription("PRODUCT3");
        Address adr = new Address("address", "city", "state", "zipCode");
        Address billingAdr = new Address("billing_address", "billing_city", "billing_state", "billing_zipCode");
        Address shippingAdr = new Address("shipping_address", "shipping_city", "shipping_state", "shipping_zipCode");

        Customer customer = new Customer(CUSTOMER_NAME, adr, "phone", "email");
        customerRepository.save(customer);

        OrderHeader orderHeader = new OrderHeader(customer, billingAdr, shippingAdr, OrderStatus.NEW,
                new OrderApproval("approver name"));
        Set<OrderLine> orderLines = Set.of(
                new OrderLine(10, product1),
                new OrderLine(3, product2),
                new OrderLine(7, product3));
        orderHeader.associateOrderLine(orderLines);
        orderHeaderRepository.save(orderHeader);
    }

    @Test
    @Order(2)
    public void lazyInitializationErrorOccurs_whenNoTransactional() {
        // LazyInitializationException is thrown because we try to fetch OrderLines field of OrderHeader which is 
        // lazily loaded/fetched outside a transaction, in a method where is no transaction. If we tried to do this 
        // inside a transactional context it would be green. As then we are inside a Hibernate transaction and 
        // Hibernate session, then Hibernate Session proxy knows that when `orderHeader.getOrderLines()` is called on
        // orderHeader object (which is managed by Hibernate Session as we are inside a transaction) 
        // Hibernate has to query the db for orderLines.
        OrderHeader orderHeader = orderHeaderRepository.findAllByCustomer_Name(CUSTOMER_NAME).get(0);

        assertThatThrownBy(
                () -> orderHeader.getOrderLines().forEach(
                        ol -> log.info("Product description: {}", ol.getProduct().getDescription())))
                .isInstanceOf(org.hibernate.LazyInitializationException.class);
    }

    @Test
    @Transactional
    @Order(3)
    public void lazyInitializationErrorDoesNotOccur_whenInsideTransaction() {
        // LazyInitializationException is thrown because we try to fetch OrderLines field of OrderHeader which is 
        // lazily loaded/fetched outside a transaction, in a method where is no transaction. If we tried to do this 
        // inside a transactional context it would be green. As then we are inside a Hibernate transaction and 
        // Hibernate session, then Hibernate Session proxy knows that when `orderHeader.getOrderLines()` is called on
        // orderHeader object (which is managed by Hibernate Session as we are inside a transaction) 
        // Hibernate has to query the db for orderLines.

        OrderHeader orderHeader = orderHeaderRepository.findAllByCustomer_Name(CUSTOMER_NAME).get(0);

        Set<OrderLine> orderLines = orderHeader.getOrderLines();

        orderLines.forEach(
                ol -> log.info("Product description: {}", ol.getProduct().getDescription()));

        assertThat(orderLines).size().isGreaterThan(0);
    }


}
