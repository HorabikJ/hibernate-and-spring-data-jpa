package guru.springframework.sdjpaintro.repositories.hibernatePrimaryKeys;

import guru.springframework.sdjpaintro.domain.hibernatePrimaryKeys.composite.AuthorComposite;
import guru.springframework.sdjpaintro.domain.hibernatePrimaryKeys.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 8/18/21.
 */
public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {
}
