package guru.springframework.sdjpaintro.inheritance.tablePerClass.notEntityBaseClass;

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
@Table(name = "table_per_class_sports_vehicle")
@Entity
public class TPCSportsVehicle extends Vehicle {

    private BigDecimal secondsTo100;

}
