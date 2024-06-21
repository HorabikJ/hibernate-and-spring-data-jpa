package guru.springframework.sdjpaintro.inheritance.mappedSuperClass;

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
@Table(name = "mapped_super_class_sports_vehicle")
@Entity
public class MSCSportsVehicle extends Vehicle {

    private BigDecimal secondsTo100;

}
