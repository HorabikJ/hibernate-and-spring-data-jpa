package guru.springframework.sdjpaintro.hibernateJavaMappings.service;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.Category;
import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.Product;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.CategoryRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductCategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    //todo write unit test for this
    @Transactional
    void associateProductWithCategory(Long categroyId, Long productId) {
        Category category = categoryRepository.getById(categroyId);
        Product product = productRepository.getById(productId);
        product.associateCategory(category);
        category.associateProduct(product);
        productRepository.save(product);
        categoryRepository.save(category);
    }

}
