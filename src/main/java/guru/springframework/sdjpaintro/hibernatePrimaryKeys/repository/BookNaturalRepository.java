package guru.springframework.sdjpaintro.hibernatePrimaryKeys.repository;

import guru.springframework.sdjpaintro.hibernatePrimaryKeys.domain.BookNatural;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 8/16/21.
 */
public interface BookNaturalRepository extends JpaRepository<BookNatural, String> {
}
