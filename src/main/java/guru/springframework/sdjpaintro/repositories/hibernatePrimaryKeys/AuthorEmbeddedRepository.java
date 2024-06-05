package guru.springframework.sdjpaintro.repositories.hibernatePrimaryKeys;

import guru.springframework.sdjpaintro.domain.hibernatePrimaryKeys.composite.AuthorEmbedded;
import guru.springframework.sdjpaintro.domain.hibernatePrimaryKeys.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 8/18/21.
 */
public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {
}
