package guru.springframework.sdjpaintro.repositories.hibernatePrimaryKeys;

import guru.springframework.sdjpaintro.domain.hibernatePrimaryKeys.BookNatural;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 8/16/21.
 */
public interface BookNaturalRepository extends JpaRepository<BookNatural, String> {
}
