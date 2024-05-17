package guru.springframework.sdjpaintro.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * As in SpringBoot 3 and above, flyway clean strategy is disabled by default, we have to enable it by setting the
 * right property. For this property look in the `application-clean.properties` file.
 * Also, you can look for example log during app startup:
 * {@code o.f.core.internal.command.DbClean        : Successfully cleaned schema `bookdb` (execution time 00:00.006s)}
 */
@Profile("clean")
@Configuration
public class FlywayDbCleanConfig {

    @Bean
    public FlywayMigrationStrategy clean() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }

}
