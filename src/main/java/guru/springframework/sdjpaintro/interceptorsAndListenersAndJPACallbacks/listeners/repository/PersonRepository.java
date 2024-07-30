package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.listeners.repository;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.listeners.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
