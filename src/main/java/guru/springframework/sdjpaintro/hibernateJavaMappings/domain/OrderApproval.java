package guru.springframework.sdjpaintro.hibernateJavaMappings.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderApproval extends BaseEntity {

    private String approvedBy;

    @OneToOne
    private OrderHeader orderHeader;

    public OrderApproval(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public void associateOrderHeader(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
    }
}
