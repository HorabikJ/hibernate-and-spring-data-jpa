package guru.springframework.sdjpaintro.hibernateJavaMappings.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Product extends BaseEntity {

    private String description;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
}
