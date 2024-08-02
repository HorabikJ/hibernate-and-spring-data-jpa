package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacks.repository;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.service.Base64EncodingService;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacks.entity.Employee;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacks.entity.EmployeeJpaCallback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@Import({Base64EncodingService.class, EmployeeJpaCallback.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldSaveEmployeeWithEncodedFieldCorrectly() {
        Employee employee = new Employee(
                "John",
                "Doe",
                new BigDecimal("100.45"),
                "classified personal info");

        Employee saved = employeeRepository.saveAndFlush(employee);

        Map<String, Object> resultMap = jdbcTemplate.queryForMap("SELECT data_to_encode FROM employee " +
                "WHERE id = ?", saved.getId());
        String dataToEncodeRawValue = resultMap.get("data_to_encode").toString();

        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("John");
        assertThat(saved.getSurname()).isEqualTo("Doe");
        assertThat(saved.getSalary()).isEqualTo(new BigDecimal("100.45"));
        assertThat(saved.getDataToEncode()).isEqualTo("classified personal info");
        assertThat(dataToEncodeRawValue).isEqualTo("Y2xhc3NpZmllZCBwZXJzb25hbCBpbmZv");
    }

    @Test
    @Sql(statements = "INSERT INTO employee " +
            "(id, name, surname, salary, data_to_encode, created_date, modified_date) " +
            "VALUES " +
            "(999, 'John', 'Smith', 100.77, 'Y2xhc3NpZmllZCBwZXJzb25hbCBpbmZv', null, null)")
    public void shouldFetchEmployeeWithEncodedFieldCorrectly() {
        Employee fetched = employeeRepository.getById(999L);

        assertThat(fetched).isNotNull();
        assertThat(fetched.getName()).isEqualTo("John");
        assertThat(fetched.getSurname()).isEqualTo("Smith");
        assertThat(fetched.getSalary()).isEqualTo(new BigDecimal("100.77"));
        assertThat(fetched.getDataToEncode()).isEqualTo("classified personal info");
    }

}
