package guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.repository;

import guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
