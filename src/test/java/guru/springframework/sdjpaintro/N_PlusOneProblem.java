package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.*;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.CustomerRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.OrderHeaderRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class N_PlusOneProblem {

    //Solutions for N+1 problem are described here: https://bulldogjob.pl/readme/eliminacja-problemu-n-1-zapytan-w-hibernate
    // Also, we can set FetchType.EAGER on @..ToMAny relation but I think that it is a bad practice as we modify the 
    // entity this change will afect all queries for that entity. It is better to modify single query. 

    private static final String CUSTOMER_NAME = "customerForNPlusOneProblem";

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Disabled
    @Test
    @Commit
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

        Customer customer = new Customer("customerForNPlusOneProblem", adr, "phone", "email");
        customerRepository.save(customer);

        List<OrderHeader> orderHeaders = new LinkedList<>();
        for (int i = 0; i <= 10_000; i++) {
            OrderHeader orderHeader = new OrderHeader(customer, billingAdr, shippingAdr, OrderStatus.NEW,
                    new OrderApproval("approver name" + i));
            Set<OrderLine> orderLines = Set.of(
                    new OrderLine(10 + i, product1),
                    new OrderLine(3 + i, product2),
                    new OrderLine(7 + i, product3));
            orderHeader.associateOrderLine(orderLines);
            orderHeaders.add(orderHeader);
        }
        orderHeaderRepository.saveAll(orderHeaders);
    }

    @Test
    public void testN_PlusOneProblem_noSolution() {
        long start = System.currentTimeMillis();
        //first we fetch the customer
        Customer customer = customerRepository.findByName(CUSTOMER_NAME).get();
        //and then all order headers that this customer have, this is where we have the problem of N+1 queries
        List<OrderHeader> orderHeaders = orderHeaderRepository.findAllByCustomer_Name(customer.getName());
        Integer totalSumOfQuantityOrdered = orderHeaders.stream()
                .map(OrderHeader::getOrderLines)
                .flatMap(Set::stream)
                .map(OrderLine::getQuantityOrdered)
                .reduce(Integer::sum)
                .get();
        long duration = System.currentTimeMillis() - start;
        log.info("Total number of products ordered = {}. Test duration = {}[ms].", totalSumOfQuantityOrdered, duration);
    }

    @Test
    public void testN_PlusOneProblem_EntityGraphSolution() {
        long start = System.currentTimeMillis();
        //first we fetch the customer
        Customer customer = customerRepository.findByName(CUSTOMER_NAME).get();
        //and then all order headers that this customer have, this is where we have the problem of N+1 queries
        List<OrderHeader> orderHeaders = orderHeaderRepository.findAllByCustomer_Name_withEntityGraph(customer.getName());
        Integer totalSumOfQuantityOrdered = orderHeaders.stream()
                .map(OrderHeader::getOrderLines)
                .flatMap(Set::stream)
                .map(OrderLine::getQuantityOrdered)
                .reduce(Integer::sum)
                .get();
        long duration = System.currentTimeMillis() - start;
        log.info("Total number of products ordered = {}. Test duration = {}[ms].", totalSumOfQuantityOrdered, duration);
    }

    @Test
    public void testN_PlusOneProblem_leftJoinFetchSolution() {
        long start = System.currentTimeMillis();
        //first we fetch the customer
        Customer customer = customerRepository.findByName(CUSTOMER_NAME).get();
        //and then all order headers that this customer have, this is where we have the problem of N+1 queries
        List<OrderHeader> orderHeaders = orderHeaderRepository.findAllByCustomer_Name_withLeftJoinFetch(customer.getName());
        Integer totalSumOfQuantityOrdered = orderHeaders.stream()
                .map(OrderHeader::getOrderLines)
                .flatMap(Set::stream)
                .map(OrderLine::getQuantityOrdered)
                .reduce(Integer::sum)
                .get();
        long duration = System.currentTimeMillis() - start;
        log.info("Total number of products ordered = {}. Test duration = {}[ms].", totalSumOfQuantityOrdered, duration);
    }

}
