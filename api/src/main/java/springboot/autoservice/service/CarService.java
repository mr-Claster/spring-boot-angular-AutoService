package springboot.autoservice.service;

import springboot.autoservice.model.Car;
import java.util.List;

public interface CarService {
    Car getById(Long id);

    Car create(Car car);

    List<Car> getAll();
}
