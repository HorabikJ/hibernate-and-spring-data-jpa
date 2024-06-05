package guru.springframework.sdjpaintro.hibernatePrimaryKeys.repository;

import guru.springframework.sdjpaintro.hibernatePrimaryKeys.domain.AuthorUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by jt on 8/15/21.
 */
public interface AuthorUuidRepository extends JpaRepository<AuthorUuid, UUID> {
}
