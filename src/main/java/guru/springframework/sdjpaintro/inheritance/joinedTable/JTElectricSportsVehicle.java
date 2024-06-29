package guru.springframework.sdjpaintro.inheritance.joinedTable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "joined_table_electric_sports_vehicle")
@Entity
public class JTElectricSportsVehicle extends JTSportsVehicle {

    private Integer batteryVolume;

}
