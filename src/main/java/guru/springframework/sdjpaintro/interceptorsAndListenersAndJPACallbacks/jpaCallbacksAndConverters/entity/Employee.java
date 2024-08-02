package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacksAndConverters.entity;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.BaseEntity;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacksAndConverters.callback.EmployeeJpaCallback;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacksAndConverters.converter.EmployeeEncodingConverter;
import jakarta.persistence.Convert;
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
    @Convert(converter = EmployeeEncodingConverter.class)
    private String secondDataToEncode;

    // There are a lot of callback annotations, just check out the package where the below annotation is located or 
    // this website https://www.baeldung.com/jpa-entity-lifecycle-events.
    // But is it not a good practice to have callbacks defined in an entity class, much better is to define them in a
    // separate class and use the @EntityListeners annotation on an entity as in this example.
    @PrePersist
    public void prePersistCallback() {
        log.info("Pre persist callback.");
    }
}
