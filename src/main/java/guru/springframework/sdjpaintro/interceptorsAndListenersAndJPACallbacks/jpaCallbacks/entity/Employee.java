package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacks.entity;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PrePersist;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Entity
@Slf4j
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners({EmployeeJpaCallback.class})
public class Employee extends BaseEntity {

    private String name;
    private String surname;
    private BigDecimal salary;
    private String dataToEncode;

    // There are a lot of callback annotations, just check out the package where the below annotation is located or 
    // this website https://www.baeldung.com/jpa-entity-lifecycle-events.
    @PrePersist
    public void prePersistCallback() {
        log.info("Pre persist callback.");
    }
}
