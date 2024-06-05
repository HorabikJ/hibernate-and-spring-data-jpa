package guru.springframework.sdjpaintro.hibernatePrimaryKeys.repository;

import guru.springframework.sdjpaintro.hibernatePrimaryKeys.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.Future;

public interface AuthorJpaRepository extends JpaRepository<Author, Long> {

    Author findAuthorByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT a FROM Author a WHERE a.lastName LIKE %:lastName%")
    List<Author> listAuthorByLastNameLike(String lastName);

    @Async
    @Query("SELECT a FROM Author a WHERE a.firstName LIKE %:firstName%")
    Future<List<Author>> listAuthorByFirstNameLike(String firstName);

    @Query(value = "SELECT COUNT(*) FROM author WHERE last_name = :lastName", nativeQuery = true)
    Long countAuthorsWithGivenLastname(String lastName);
}
