package guru.springframework.sdjpaintro.interceptorsAndListeners.repository;

import guru.springframework.sdjpaintro.interceptorsAndListeners.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

}
