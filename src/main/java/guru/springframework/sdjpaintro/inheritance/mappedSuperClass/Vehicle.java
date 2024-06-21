package guru.springframework.sdjpaintro.inheritance.mappedSuperClass;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.BaseEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// long story short: @MappedSuperClass vs. @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS):
// Class annotated with @MappedSuperClass does not have a separate table for it, no matter if it is an abstract class
// or concrete class. The class annotated with @MappedSuperClass can not be an @Entity.
//
// Class annotated with @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS), can be an @Entity or not.
// If this class is an @Entity, it has a separate table for it. If it is not an @Entity, it does not have a separate 
// table for it.
// Basically if, class annotated with @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) is not an @Entity, the
// setup in db looks the same as in @MappedSuperClass example. I do not know how Hibernate interprets it though when 
// creating queries.

// more here: https://gandrille.github.io/tech-notes/Languages/Java/API/JPA_(Hibernate)/Hibernate_resources/2016%20Inheritance%20strategies%20with%20JPA%20and%20Hibernate.pdf

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class Vehicle extends BaseEntity {

    private int horsePower;
    private String make;
    private String model;

}
