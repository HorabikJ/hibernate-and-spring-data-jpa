package guru.springframework.sdjpaintro.hibernateFullDemo.repository;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void getCategoryByDescription() {
        // CAT1 and PRODUCT1 is inserted into db by flyway script
        Product product = productRepository.getByDescription("PRODUCT1");

        assertThat(product).isNotNull();
        assertThat(product.getDescription()).isEqualTo("PRODUCT1");
        assertThat(product.getCategories()).size().isEqualTo(2);
        assertThat(product.getCategories())
                .anySatisfy(category -> category.getDescription().equals("CAT2"))
                .anySatisfy(category -> category.getDescription().equals("CAT1"));
    }


}
