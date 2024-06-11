package guru.springframework.sdjpaintro.hibernateFullDemo.service;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Category;
import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Product;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.CategoryRepository;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.ProductRepository;
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

    @Transactional
    void associateProductWithCategory(Long categroyId, Long productId) {
        Category category = categoryRepository.getById(categroyId);
        Product product = productRepository.getById(productId);
        product.associateCategory(category);
        category.associateProduct(product);
        productRepository.save(product);
        categoryRepository.save(category);
//        productRepository.saveAndFlush(product);
//        categoryRepository.saveAndFlush(category);
        
//      Interesting fact. Above 2 commented lines "..saveAndFlush()" would not work. The reason for that is when 
//      calling first "saveAndFlush" we save and flush only one part of bi-directional product <-> 
//      category relation, the product part, which violates the db constraint as this relation is bi-directional, so to 
//      properly save it in db we have to save product that have its category and category that has its product at 
//      once. It is like we would like to execute statement `insert into product_categories (product_id, category_id)
//      values (1, null);`, which violates the constraint for this table as a primary key for this table is composite 
//      and consist of 2 columns (product_id, category_id) so we have to insert values for 2 columns in order to 
//      create a row.
//      When using save() instead of saveAndFlush(), save() only updates the Hibernate session with the state of our 
//      product and category but does not execute any sql when save() is invoked. So Hibernate session have correct 
//      product and category persisted objects in it with correct relations and when Hibernate session ends it 
//      flushes its state with correct relations to db at once, which results in correct relations persisted
//      in db.  
    }

}
