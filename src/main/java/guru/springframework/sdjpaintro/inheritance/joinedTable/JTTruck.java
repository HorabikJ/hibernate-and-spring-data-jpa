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
@Table(name = "joined_table_truck")
@Entity
public class JTTruck extends JTVehicle {

    private int loadLimitKg;

}
