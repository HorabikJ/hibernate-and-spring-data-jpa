package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * By default @DataJpaTest uses the H2 in memory database, we want to disable this default scenario. In this 
 * integration test we want to run against the real PostgreSQL database. That is why we chose the profile "local" so 
 * DataSource bean will be configured as per properties from "application-local.properties". Next, we want to disable
 * the autoconfiguration of H2 database by using 
 * {@code @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)}, so H2 will not be 
 * autoconfigured, and we will be provided with the DataSource as per "local" properties.
 * It can be seen which DB is brought up by analyzing the startup logs of this test.
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySQLIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testMySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);

    }

}


