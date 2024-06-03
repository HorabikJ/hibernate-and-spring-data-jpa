package guru.springframework.sdjpaintro.domain.composite;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * In this class we want to use the same table as in {@link guru.springframework.sdjpaintro.domain.composite.AuthorComposite}
 * class. We only change the type of the PK in Java, instead of 2 different fields {@code String firstName} and
 * {@code String lastName}, we have a field with type {@code NameId} which is more elegant and object-oriented.
 */
@Entity
@Table(name = "author_composite")
public class AuthorEmbedded {

    @EmbeddedId
    private NameId nameId;

    private String country;

    public AuthorEmbedded() {
    }

    public AuthorEmbedded(NameId nameId) {
        this.nameId = nameId;
    }

    public NameId getNameId() {
        return nameId;
    }

    public void setNameId(NameId nameId) {
        this.nameId = nameId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
