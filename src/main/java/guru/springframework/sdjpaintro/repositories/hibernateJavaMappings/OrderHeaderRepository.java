package guru.springframework.sdjpaintro.repositories.hibernateJavaMappings;

import guru.springframework.sdjpaintro.domain.hibernateJavaMappings.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
}
