package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.listeners.repository;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.encoder.EntityEncoder;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.service.Base64EncodingService;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.listeners.ListenersConfig;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.listeners.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackageClasses = ListenersConfig.class)
@Import({Base64EncodingService.class, EntityEncoder.class})
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testSaveAndRetrievePersonInOneHibernateSession() {
        Person person = new Person("Jack", "Strong", "dataToEncode");
        Person savedPerson = personRepository.saveAndFlush(person);

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
    @Sql(statements = "INSERT INTO person (id, name, surname, personal_data_to_encode)" +
            " VALUES (444, 'Bill', 'Gates', 'ZGF0YVRvRW5jb2Rl')")
    public void testFetchPersonFromDbAndDecodeItsDataInDifferentHibernateSession() {
        Person fetched = personRepository.getById(444L);

        assertThat(fetched.getId()).isNotNull();
        assertThat(fetched.getName()).isEqualTo("Bill");
        assertThat(fetched.getSurname()).isEqualTo("Gates");
        assertThat(fetched.getPersonalDataToEncode()).isEqualTo("dataToEncode");
    }

}
