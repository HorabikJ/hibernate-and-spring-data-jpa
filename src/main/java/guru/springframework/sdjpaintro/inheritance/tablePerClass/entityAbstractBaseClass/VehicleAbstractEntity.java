package guru.springframework.sdjpaintro.inheritance.tablePerClass.entityAbstractBaseClass;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

//   To be honest I do not see any point of having an `abstract` class which is also an @Entity, as we can not 
//   instantiate the object of this class for the purpose of using this object in a db query. I created an example 
//   like that just for learning/educational purposes.

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Table(name = "vehicle_entity_abstract_parent")
public abstract class VehicleAbstractEntity {

    // When using @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS), we can not use 
    // @GeneratedValue(strategy = GenerationType.IDENTITY) on a parent @Entity class. We have to use 
    // @GeneratedValue(strategy = GenerationType.TABLE).
    // It is  like that because when base class is @Entity, Hibernate creates a `hibernate_sequences` table which 
    // serves as id source for the @Entity classes that are children of base @Entity. 

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
