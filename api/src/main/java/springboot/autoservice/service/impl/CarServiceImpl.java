package springboot.autoservice.service.impl;

import springboot.autoservice.model.Car;
import springboot.autoservice.repository.CarRepository;
import springboot.autoservice.service.CarService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car getById(Long id) {
        return carRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't get car by id " + id));
    }

    @Override
    public Car create(Car car) {
        return carRepository.save(car);
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }
}
