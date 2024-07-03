package guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.entity;

import guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.interceptor.EncodedString;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EncodedString
    @NotBlank
    private String creditCardNumber;

    private String cvv;

    private String expirationDate;

    public CreditCard(String creditCardNumber, String cvv, String expirationDate) {
        this.creditCardNumber = creditCardNumber;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }
}
