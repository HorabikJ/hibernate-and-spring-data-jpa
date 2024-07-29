package guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.repository;

import guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.ListenersConfig;
import guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackageClasses = ListenersConfig.class)
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testSave() {
        Person person = new Person("Jack", "Strong", "dataToEncode");
        Person savedPerson = personRepository.save(person);

        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo("Jack");
        assertThat(savedPerson.getSurname()).isEqualTo("Strong");
    }

}
