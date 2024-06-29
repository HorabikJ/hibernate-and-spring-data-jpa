package guru.springframework.sdjpaintro.inheritance.tablePerClass.entityAbstractBaseClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "table_per_class_truck_entity_abstract_parent")
@Entity
public class TPCTruckEntityAbstractParent extends TPCVehicleAbstractEntity {

    private int loadLimitKg;

}
