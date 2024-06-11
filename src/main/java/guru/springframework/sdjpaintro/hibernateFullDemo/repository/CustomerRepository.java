package guru.springframework.sdjpaintro.hibernateFullDemo.repository;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByName(String name);

}
