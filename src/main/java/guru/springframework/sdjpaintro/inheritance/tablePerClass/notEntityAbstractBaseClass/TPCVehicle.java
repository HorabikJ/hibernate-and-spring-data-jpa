package guru.springframework.sdjpaintro.inheritance.tablePerClass.notEntityAbstractBaseClass;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.BaseEntity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// I would recommend this approach rather than in 
// `guru.springframework.sdjpaintro.inheritance.tablePerClass.entityConcreteBaseClass` package or in 
// `guru.springframework.sdjpaintro.inheritance.tablePerClass.entityAbstractBaseClass` package.

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TPCVehicle extends BaseEntity {

    private int horsePower;
    private String make;
    private String model;

}
