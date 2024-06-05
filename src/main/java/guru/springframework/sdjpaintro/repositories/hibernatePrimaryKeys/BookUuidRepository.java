package guru.springframework.sdjpaintro.repositories.hibernatePrimaryKeys;

import guru.springframework.sdjpaintro.domain.hibernatePrimaryKeys.BookUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by jt on 8/15/21.
 */
public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {
}
