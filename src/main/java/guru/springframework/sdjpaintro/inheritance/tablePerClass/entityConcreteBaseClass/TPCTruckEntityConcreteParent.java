package guru.springframework.sdjpaintro.inheritance.tablePerClass.entityConcreteBaseClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "table_per_class_truck_entity_concrete_parent")
@Entity
public class TPCTruckEntityConcreteParent extends TPCVehicleConcreteEntity {

    private int loadLimitKg;

}
