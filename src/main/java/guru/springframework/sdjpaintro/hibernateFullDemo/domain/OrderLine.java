package guru.springframework.sdjpaintro.hibernateFullDemo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.*;

@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class OrderLine extends BaseEntity {

    private Integer quantityOrdered;
    @ManyToOne
    private OrderHeader orderHeader;
    @ManyToOne
    private Product product;
    @Version
    private Integer version;

    public OrderLine(Integer quantityOrdered, Product product) {
        this.quantityOrdered = quantityOrdered;
        this.product = product;
    }

}
