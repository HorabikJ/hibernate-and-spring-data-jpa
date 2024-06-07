package guru.springframework.sdjpaintro.hibernateJavaMappings.service;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.Category;
import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.Product;
import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.ProductStatus;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.CategoryRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ProductCategoryService.class)
class ProductCategoryServiceTest {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void test() {
        Category category = new Category("category one");
        Product product = new Product("product one", ProductStatus.NEW);

        Product savedProduct = productRepository.saveAndFlush(product);
        Category savedCategory = categoryRepository.saveAndFlush(category);

        productCategoryService.associateProductWithCategory(savedCategory.getId(), savedProduct.getId());

        Product fetchedProduct = productRepository.getById(savedProduct.getId());
        Category fetchedCategory = categoryRepository.getById(savedCategory.getId());

        assertThat(fetchedCategory.getProducts()).size().isEqualTo(1);
        assertThat(fetchedCategory.getProducts())
                .extracting("description", String.class)
                .contains("product one");
        assertThat(fetchedCategory.getProducts())
                .extracting("productStatus", ProductStatus.class)
                .contains(ProductStatus.NEW);

        assertThat(fetchedProduct.getCategories()).size().isEqualTo(1);
        assertThat(fetchedProduct.getCategories())
                .extracting("description", String.class)
                .contains("category one");
    }


}
