package guru.springframework.sdjpaintro.hibernateFullDemo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp // Hibernate specific annotation
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp // Hibernate specific annotation
    private Timestamp modifiedDate;

}
