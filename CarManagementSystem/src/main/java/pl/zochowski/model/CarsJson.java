package pl.zochowski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class CarsJson {
    private Set<Car> cars;
}
