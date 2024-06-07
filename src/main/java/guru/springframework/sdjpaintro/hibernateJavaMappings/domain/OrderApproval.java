package guru.springframework.sdjpaintro.hibernateJavaMappings.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderApproval extends BaseEntity {

    private String approvedBy;
    
    @OneToOne
    private OrderHeader orderHeader;
    
}
