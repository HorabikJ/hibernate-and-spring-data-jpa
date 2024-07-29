package guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.entity;

import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.service.Base64EncodingService;
import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.service.EncodingService;
import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.template.EncodedString;
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

    private static final EncodingService ENCODING_SERVICE = new Base64EncodingService();

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

    @PostLoad
    public void decodeCreditCardNumber() {
        creditCardNumber = ENCODING_SERVICE.decode(creditCardNumber);
    }

}
