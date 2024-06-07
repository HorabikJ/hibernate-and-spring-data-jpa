package guru.springframework.sdjpaintro.hibernateJavaMappings.repository;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void getCategoryByDescription() {
        // CAT1 and PRODUCT1 is inserted into db by flyway script
        Category category = categoryRepository.getByDescription("CAT1");

        assertThat(category).isNotNull();
        assertThat(category.getDescription()).isEqualTo("CAT1");
        assertThat(category.getProducts()).size().isEqualTo(2);
        assertThat(category.getProducts())
                .anySatisfy(product -> product.getDescription().equals("PRODUCT1"))
                .anySatisfy(product -> product.getDescription().equals("PRODUCT1"));
    }

}
