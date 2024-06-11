package guru.springframework.sdjpaintro.hibernateFullDemo.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    private String name;

    @Embedded
    private Address address;

    private String phone;
    private String email;

    @OneToMany(mappedBy = "customer")
    @EqualsAndHashCode.Exclude
    private Set<OrderHeader> orderHeaders = new LinkedHashSet<>();

    public Customer(String name, Address address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
    
}
