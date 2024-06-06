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
    @ManyToOne //bidirectional
    private OrderHeader orderHeader;
    @ManyToOne //unidirectional
    private Product product;

    public OrderLine(Integer quantityOrdered, Product product) {
        this.quantityOrdered = quantityOrdered;
        this.product = product;
    }

}
