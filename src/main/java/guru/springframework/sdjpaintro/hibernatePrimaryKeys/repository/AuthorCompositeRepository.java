package guru.springframework.sdjpaintro.hibernatePrimaryKeys.repository;

import guru.springframework.sdjpaintro.hibernatePrimaryKeys.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.hibernatePrimaryKeys.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 8/18/21.
 */
public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {
}
