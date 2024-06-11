package guru.springframework.sdjpaintro.hibernateFullDemo.repository;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getByDescription(String description);

    // PESSIMISTIC_WRITE lock on findById() method causes that the sql statement that will be executed for this 
    // repository method will be `select for update`, which applies the lock on the selected row until the end of 
    // transaction.  
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findById(Long aLong);
}
