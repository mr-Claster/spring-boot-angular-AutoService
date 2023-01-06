package springboot.autoservice.controller;

import springboot.autoservice.dto.request.CarRequestDto;
import springboot.autoservice.dto.response.CarResponseDto;
import springboot.autoservice.model.Car;
import springboot.autoservice.model.Owner;
import springboot.autoservice.service.CarService;
import springboot.autoservice.service.OwnerService;
import springboot.autoservice.dto.mapper.CarMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final CarMapper carMapper;

    public CarController(CarService carService,
                         CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponseDto create(@RequestBody CarRequestDto carRequestDto) {
        Car car = carMapper.mapToModel(carRequestDto);
        return carMapper.mapToDto(carService.create(car));
    }

    @PutMapping("/{id}")
    public CarResponseDto update(@PathVariable Long id,
                                       @RequestBody CarRequestDto carRequestDto) {
        Car car = carMapper.mapToModel(carRequestDto);
        car.setId(id);
        return carMapper.mapToDto(carService.create(car));
    }
}
