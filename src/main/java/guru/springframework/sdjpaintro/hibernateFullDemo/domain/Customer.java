package guru.springframework.sdjpaintro.hibernateFullDemo.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    @Size(max = 50)
    @NotBlank
    private String name;

    @Valid // on embedded fields we need to put @Valid
    @Embedded
    private Address address;

    @Size(max = 20)
    @NotBlank
    private String phone;
    //    @Email not using now as many tests would fail
    @Size(max = 255)
    @NotBlank
    private String email;

    @OneToMany(mappedBy = "customer")
    @EqualsAndHashCode.Exclude
    private Set<OrderHeader> orderHeaders = new LinkedHashSet<>();

    @Version
    private Integer version;

    public Customer(String name, Address address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

}
