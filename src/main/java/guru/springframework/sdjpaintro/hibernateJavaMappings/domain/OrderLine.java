package guru.springframework.sdjpaintro.hibernateJavaMappings.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class OrderLine extends BaseEntity {

    private Integer quantityOrdered;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private OrderHeader orderHeader;

    public OrderLine(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

}
