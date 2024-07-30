package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.interceptors.repository;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.interceptors.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

}
