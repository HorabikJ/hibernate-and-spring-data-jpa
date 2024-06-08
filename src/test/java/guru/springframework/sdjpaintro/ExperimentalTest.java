package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.*;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.CategoryRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.CustomerRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.OrderHeaderRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExperimentalTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @BeforeEach
    public void setupTestData() {
        Product product1 = new Product("product_1", ProductStatus.NEW);
        Product product2 = new Product("product_2", ProductStatus.NEW);
        Category category1 = new Category("category_1");
        Category category2 = new Category("category_2");
        productRepository.saveAll(List.of(product1, product2));
        categoryRepository.saveAll(List.of(category1, category2));
        product1.associateCategory(category1);
        product2.associateCategory(category2);
        productRepository.saveAll(List.of(product1, product2));
        categoryRepository.saveAll(List.of(category1, category2));

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
            orderHeaderRepository.save(orderHeader);
        }
    }

    @Test
    public void test() {
        System.out.println("test");
    }

}
