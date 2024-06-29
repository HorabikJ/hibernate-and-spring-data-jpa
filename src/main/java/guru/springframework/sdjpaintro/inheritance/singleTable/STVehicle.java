package guru.springframework.sdjpaintro.inheritance.singleTable;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity //In order for @Inheritance(strategy = InheritanceType.SINGLE_TABLE) to work this class has to be annotated 
// with @Entity
@DiscriminatorColumn(name = "vehicle_type")
@Table(name = "single_table_vehicle")
public abstract class STVehicle extends BaseEntity {

    private int horsePower;
    private String make;
    private String model;

}
