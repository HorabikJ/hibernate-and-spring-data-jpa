package guru.springframework.sdjpaintro.hibernateJavaMappings.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Address {

    private String address;
    private String city;
    private String state;
    private String zipCode;

}
