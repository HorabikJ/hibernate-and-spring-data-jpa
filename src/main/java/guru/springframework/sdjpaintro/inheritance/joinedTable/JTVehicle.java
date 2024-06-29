package guru.springframework.sdjpaintro.inheritance.joinedTable;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "joined_table_vehicle")
public abstract class JTVehicle extends BaseEntity {

    private int horsePower;
    private String make;
    private String model;

}
