package guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.entity;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.BaseEntity;
import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.annotations.EncodedString;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity {

    private String name;
    private String surname;
    @EncodedString
    private String personalDataToEncode;

}
