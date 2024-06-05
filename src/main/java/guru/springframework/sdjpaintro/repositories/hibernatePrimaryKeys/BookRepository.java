package guru.springframework.sdjpaintro.repositories.hibernatePrimaryKeys;

import guru.springframework.sdjpaintro.domain.hibernatePrimaryKeys.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 6/12/21.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}
