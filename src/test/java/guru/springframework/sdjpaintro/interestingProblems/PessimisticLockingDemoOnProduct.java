package guru.springframework.sdjpaintro.interestingProblems;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Product;
import guru.springframework.sdjpaintro.hibernateFullDemo.domain.ProductStatus;
import guru.springframework.sdjpaintro.hibernateFullDemo.service.locking.pesimistic.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("local")
public class PessimisticLockingDemoOnProduct {

    @Autowired
    private ProductService productService;

    @Test
    //Strange behaviour that needs to be investigated. When this test method is @Transactional, hibernate fires only 
    // 2 sql queries: 1. `insert into`, 2. `select for update`. Whe this method is not @Transactional, Hibernate 
    // fires 3 queries: 1. `insert into`, 2. `select for update`, 3. `update`.
    // I am wondering why in @Transactional context Hibernate does not fire `update` sql at the end. The state of the
    // Hibernate session has to be somehow flushed to db so why there `update` sql is missing?
    public void should_update_quantity_on_hand() {
        Product newProduct = new Product("super cool product", ProductStatus.NEW);
        newProduct.setQuantityOnHand(15);

        Product savedProduct = productService.saveProduct(newProduct);

        assertThat(savedProduct.getQuantityOnHand()).isEqualTo(15);

        Product updated = productService.updateQuantityOnHand(savedProduct.getId(), 30);

        assertThat(updated.getQuantityOnHand()).isEqualTo(30);
    }

    @Test
    public void can_not_use_select_for_update_sql_in_read_only_transaction() {
        assertThatThrownBy(() -> productService.findById(1L)).isInstanceOf(JpaSystemException.class);
    }

}
