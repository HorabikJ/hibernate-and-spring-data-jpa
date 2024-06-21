package guru.springframework.sdjpaintro.inheritance.tablePerClass.notEntityBaseClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "table_per_class_truck")
@Entity
public class TPCTruck extends Vehicle {

    private int loadLimitKg;

}
