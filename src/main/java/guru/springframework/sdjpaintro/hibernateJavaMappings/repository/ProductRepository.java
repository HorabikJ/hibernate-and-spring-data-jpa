package guru.springframework.sdjpaintro.hibernateJavaMappings.repository;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
