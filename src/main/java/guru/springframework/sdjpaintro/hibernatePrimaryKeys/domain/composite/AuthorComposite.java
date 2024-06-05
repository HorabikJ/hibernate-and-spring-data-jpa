package guru.springframework.sdjpaintro.hibernatePrimaryKeys.domain.composite;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * In {@code AuthorComposite} class we use composite PK. We need to specify the class that is an abstraction for
 * composite PK by the {@code @IdClass} annotation.
 */
@Entity
@IdClass(NameId.class)
@Table(name = "author_composite")
public class AuthorComposite {

    @Id
    private String firstName;

    @Id
    private String lastName;
    private String country;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
