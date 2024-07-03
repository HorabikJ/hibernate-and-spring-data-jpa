package guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.repository;

import guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

}
