package guru.springframework.sdjpaintro.inheritance.tablePerClass.entityConcreteBaseClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "table_per_class_sports_vehicle_entity_concrete_parent")
@Entity
public class TPCSportsVehicleEntityConcreteParent extends VehicleConcreteEntity {

    private BigDecimal secondsTo100;

}
