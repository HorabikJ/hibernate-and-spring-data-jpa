package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.*;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.CategoryRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.CustomerRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.OrderHeaderRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class ExperimentalTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    private static final List<Long> IDS = new LinkedList<>();

    @Test
    @Commit
    public void setupTestData() {
        // Products inserted via flyway script, I am using those because when I tried to insert products and 
        // categories inside of the test it did not work. 
        // It is always better to insert data to db via executing some sql script so before all test execution db is 
        // loaded with test data. Loading test data to db inside tests is not good practice, but for the sake of 
        // simplicity we do this here. We can load test data to db by using @Sql annotation on a test method.
        Product product1 = productRepository.getByDescription("PRODUCT1");
        Product product2 = productRepository.getByDescription("PRODUCT2");
        List<OrderHeader> orderHeaders = new LinkedList<>();
        for (int i = 0; i <= 100; i++) {
            Address adr = new Address("address" + i, "city" + i, "state" + i, "zipCode" + i);
            Address billingAdr = new Address("billing_address" + i, "billing_city" + i, "billing_state" + i, "billing_zipCode" + i);
            Address shippingAdr = new Address("shipping_address" + i, "shipping_city" + i, "shipping_state" + i, "shipping_zipCode" + i);

            Customer customer = new Customer("name" + i, adr, "phone" + i, "email" + i);
            customerRepository.save(customer);

            OrderHeader orderHeader = new OrderHeader(customer, billingAdr, shippingAdr, OrderStatus.NEW, "approved_by" + i);
            Set<OrderLine> orderLines = Set.of(
                    new OrderLine(10 + i, product1),
                    new OrderLine(5 + i, product2));
            orderHeader.associateOrderLine(orderLines);
            orderHeaders.add(orderHeader);
        }
        List<OrderHeader> saved = orderHeaderRepository.saveAll(orderHeaders);
        saved.forEach(or -> IDS.add(or.getId()));
    }

    @Test
    public void test() {
        Optional<OrderHeader> orderHeader = orderHeaderRepository.findById(IDS.get(51));
        System.out.println("lalala");
    }

}
