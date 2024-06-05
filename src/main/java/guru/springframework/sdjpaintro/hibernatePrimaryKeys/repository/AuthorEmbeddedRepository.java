package guru.springframework.sdjpaintro.hibernatePrimaryKeys.repository;

import guru.springframework.sdjpaintro.hibernatePrimaryKeys.domain.composite.AuthorEmbedded;
import guru.springframework.sdjpaintro.hibernatePrimaryKeys.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 8/18/21.
 */
public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {
}
