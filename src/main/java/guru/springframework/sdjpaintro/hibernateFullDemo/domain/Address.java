package guru.springframework.sdjpaintro.hibernateFullDemo.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Address {

    @Size(max = 30)
    @NotBlank
    private String address;
    @Size(max = 30)
    @NotBlank
    private String city;
    @Size(max = 30)
    @NotBlank
    private String state;
    @Size(max = 30)
    @NotBlank
    private String zipCode;

}
