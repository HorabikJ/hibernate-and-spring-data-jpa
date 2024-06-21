package guru.springframework.sdjpaintro.inheritance.mappedSuperClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mapped_super_class_truck")
@Entity
public class MSCTruck extends Vehicle {

    private int loadLimitKg;
    
}
