package guru.springframework.sdjpaintro.inheritance.tablePerClass.entityConcreteBaseClass;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Table(name = "vehicle_entity_concrete_parent")
public class Vehicle {

    // When using @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS), we can not use 
    // @GeneratedValue(strategy = GenerationType.IDENTITY) on a parent @Entity class. We have to use 
    // @GeneratedValue(strategy = GenerationType.TABLE).
    // That is why when base class is @Entity, Hibernate creates a `hibernate_sequences` table which serves as id 
    // source for the @Entity classes that are children of base @Entity. 

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @CreationTimestamp // Hibernate specific annotation
    @Column(updatable = false)
    private Timestamp createdDate;
    @UpdateTimestamp // Hibernate specific annotation
    private Timestamp modifiedDate;
    private int horsePower;
    private String make;
    private String model;

}
