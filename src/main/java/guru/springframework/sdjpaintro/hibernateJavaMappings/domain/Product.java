package guru.springframework.sdjpaintro.hibernateJavaMappings.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Product extends BaseEntity {

    private String description;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
    @EqualsAndHashCode.Exclude
    @ManyToMany //bidirectional
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public Product(String description, ProductStatus productStatus) {
        this.description = description;
        this.productStatus = productStatus;
    }

    public void associateCategory(Category category) {
        categories.add(category);
    }

}
