package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.listeners.repository;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.encoder.EntityEncoder;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.service.Base64EncodingService;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.listeners.ListenersConfig;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.listeners.entity.Person;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackageClasses = ListenersConfig.class)
@Import({Base64EncodingService.class, EntityEncoder.class})
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static Long personId;
    
    @Test
    @Order(1)
    @Commit
    public void testSaveAndRetrievePersonInOneHibernateSession() {
        Person person = new Person("Jack", "Strong", "dataToEncode");
        Person savedPerson = personRepository.saveAndFlush(person);

        personId = savedPerson.getId();
        
        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo("Jack");
        assertThat(savedPerson.getSurname()).isEqualTo("Strong");
        assertThat(savedPerson.getPersonalDataToEncode()).isEqualTo("dataToEncode");

        Map<String, Object> dbRow = jdbcTemplate.queryForMap("SELECT * FROM person WHERE id = ?", savedPerson.getId());
        String personalDataToEncode = dbRow.get("personal_data_to_encode").toString();

        assertThat(personalDataToEncode).isEqualTo("ZGF0YVRvRW5jb2Rl");

        Person fetched = personRepository.getById(savedPerson.getId());

        assertThat(fetched.getId()).isNotNull();
        assertThat(fetched.getName()).isEqualTo("Jack");
        assertThat(fetched.getSurname()).isEqualTo("Strong");
        assertThat(fetched.getPersonalDataToEncode()).isEqualTo("dataToEncode");
    }

    @Test
    @Order(2)
    public void testFetchPersonFromDbAndDecodeItsDataInDifferentHibernateSession() {
        Person fetched = personRepository.getById(personId);

        assertThat(fetched.getId()).isNotNull();
        assertThat(fetched.getName()).isEqualTo("Jack");
        assertThat(fetched.getSurname()).isEqualTo("Strong");
        assertThat(fetched.getPersonalDataToEncode()).isEqualTo("dataToEncode");
    }
}
