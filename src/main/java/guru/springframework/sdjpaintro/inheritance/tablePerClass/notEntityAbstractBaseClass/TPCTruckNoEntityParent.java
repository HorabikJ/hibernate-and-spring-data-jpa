package guru.springframework.sdjpaintro.inheritance.tablePerClass.notEntityAbstractBaseClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "table_per_class_truck_no_entity_parent")
@Entity
public class TPCTruckNoEntityParent extends Vehicle {

    private int loadLimitKg;

}
