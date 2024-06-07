package guru.springframework.sdjpaintro.hibernateJavaMappings.repository;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {

}
