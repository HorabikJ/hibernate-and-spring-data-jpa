package guru.springframework.sdjpaintro.hibernateFullDemo.service.locking.pesimistic;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Product;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.getById(id);
    }


}
