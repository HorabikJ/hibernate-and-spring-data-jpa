package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.hibernatePrimaryKeys.domain.Book;
import guru.springframework.sdjpaintro.hibernatePrimaryKeys.repository.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
@DataJpaTest brings up only context with @Repository classes, to give minimum context for testing repositories. So, 
because of that the {@link guru.springframework.sdjpaintro.bootstrap.DataInitializer} is not run as it would be with 
@SpringBootTest class. Also, @DataJpaTest consist of @Transactional, so all test in this class are @Transactional, 
which is not present in @SpringBootTest.
By adding {@code @ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})} we add bean 
{@link guru.springframework.sdjpaintro.bootstrap.DataInitializer} to the context of this test, so this bean will be 
initialized with the run of below test class.

{@code @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)} is added because we want to 
configure our H2 in memory DB as per the properties in `application.properties`, not with SpringBoot default 
autoconfigure values.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpringBootJpaTestSlice {

    @Autowired
    BookRepository bookRepository;

    @Commit
    @Order(1)
    @Test
    void testJpaTestSplice() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);

        bookRepository.save(new Book("My Book", "1235555", "Self", null));

        long countAfter = bookRepository.count();

        assertThat(countBefore).isLessThan(countAfter);
    }

    @Order(2)
    @Test
    void testJpaTestSpliceTransaction() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(3);

    }
}
