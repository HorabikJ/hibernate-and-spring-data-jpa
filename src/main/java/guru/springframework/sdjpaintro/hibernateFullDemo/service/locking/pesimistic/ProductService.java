package guru.springframework.sdjpaintro.hibernateFullDemo.service.locking.pesimistic;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Product;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateQuantityOnHand(Long productId, Integer quantityOnHand) {
        // Below findById() repo method is with `@Lock(LockModeType.PESSIMISTIC_WRITE)` that results in sql 
        // `select for update` to be executed for this repository method which applies the lock on selected row and 
        // this lock is active until the end of the transaction. 
        Product product = productRepository.findById(productId).orElseThrow();

        product.setQuantityOnHand(quantityOnHand);

        return productRepository.save(product);
    }

    // By default, in Jpa every standalone repository call is wrapped in a transaction if there is no current active 
    // transaction that a given call is executed inside. As in below example, the `productRepository.findById(id)` 
    // will be executed inside an implicit transaction.
    // The below method fails, as `productRepository.findById(id)` has a pessimistic lock on it that is implemented 
    // with the usage of `@Lock(LockModeType.PESSIMISTIC_WRITE)` and results in a following sql statement `select for
    // update`. And it is not allowed to use that lock in readonly transaction. I think that Jpa/Hibernate is clever 
    // enough to distinguish a DML and DQL statements and applu readonly transaction to DQL only. 
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.getById(id);
    }


}
