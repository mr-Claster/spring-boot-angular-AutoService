package springboot.autoservice.dto.mapper;

import springboot.autoservice.dto.request.CarRequestDto;
import springboot.autoservice.dto.response.CarResponseDto;
import springboot.autoservice.model.Car;
import springboot.autoservice.service.OwnerService;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {
    private final OwnerService ownerService;

    public CarMapper(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    public Car mapToModel(CarRequestDto carRequestDto) {
        Car car = new Car();
        car.setBrand(carRequestDto.getBrand());
        car.setYear(carRequestDto.getYear());
        car.setModel(carRequestDto.getModel());
        car.setSerialNumber(carRequestDto.getSerialNumber());
        car.setOwner(ownerService.getById(carRequestDto.getOwnerId()));
        return car;
    }

    public CarResponseDto mapToDto(Car car) {
        CarResponseDto carResponseDto = new CarResponseDto();
        carResponseDto.setId(car.getId());
        carResponseDto.setBrand(car.getBrand());
        carResponseDto.setModel(car.getModel());
        carResponseDto.setYear(car.getYear());
        carResponseDto.setSerialNumber(car.getSerialNumber());
        carResponseDto.setOwnerId(car.getOwner().getId());
        return carResponseDto;
    }
}
