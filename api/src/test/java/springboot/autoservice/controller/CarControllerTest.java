package springboot.autoservice.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import springboot.autoservice.dto.mapper.CarMapper;
import springboot.autoservice.dto.request.CarRequestDto;
import springboot.autoservice.dto.response.CarResponseDto;
import springboot.autoservice.model.Car;
import springboot.autoservice.service.CarService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {
    @MockBean
    private CarService carService;
    @MockBean
    private CarMapper carMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateCar() {
        CarRequestDto carRequestDto = new CarRequestDto();
        carRequestDto.setYear(1234);
        carRequestDto.setBrand("brand");
        carRequestDto.setModel("model");
        carRequestDto.setSerialNumber("serial number");

        Car car = new Car();
        car.setYear(1234);
        car.setBrand("brand");
        car.setModel("model");
        car.setSerialNumber("serial number");
        Mockito.when(carMapper.mapToModel(carRequestDto)).thenReturn(car);

        Car carWithId = new Car();
        carWithId.setId(1L);
        carWithId.setYear(1234);
        carWithId.setBrand("brand");
        carWithId.setModel("model");
        carWithId.setSerialNumber("serial number");
        Mockito.when(carService.create(car)).thenReturn(carWithId);

        CarResponseDto carResponseDto = new CarResponseDto();
        carResponseDto.setId(1L);
        carResponseDto.setYear(1234);
        carResponseDto.setBrand("brand");
        carResponseDto.setModel("model");
        carResponseDto.setSerialNumber("serial number");
        Mockito.when(carMapper.mapToDto(carWithId)).thenReturn(carResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(carRequestDto)
                .when()
                .post("/cars")
                .then()
                .statusCode(201)
                .body("id", Matchers.equalTo(1))
                .body("year", Matchers.equalTo(1234))
                .body("brand", Matchers.equalTo("brand"))
                .body("model", Matchers.equalTo("model"))
                .body("serialNumber", Matchers.equalTo("serial number"));
    }

    @Test
    void shouldUpdateCar() {
        CarRequestDto carRequestDto = new CarRequestDto();
        carRequestDto.setYear(1234);
        carRequestDto.setBrand("brand");
        carRequestDto.setModel("model");
        carRequestDto.setSerialNumber("serial number");

        Car car = new Car();
        car.setYear(1234);
        car.setBrand("brand");
        car.setModel("model");
        car.setSerialNumber("serial number");
        Mockito.when(carMapper.mapToModel(carRequestDto)).thenReturn(car);

        Car carWithId = new Car();
        carWithId.setId(1L);
        carWithId.setYear(1234);
        carWithId.setBrand("brand");
        carWithId.setModel("model");
        carWithId.setSerialNumber("serial number");
        Mockito.when(carService.create(carWithId)).thenReturn(carWithId);

        CarResponseDto carResponseDto = new CarResponseDto();
        carResponseDto.setId(1L);
        carResponseDto.setYear(1234);
        carResponseDto.setBrand("brand");
        carResponseDto.setModel("model");
        carResponseDto.setSerialNumber("serial number");
        Mockito.when(carMapper.mapToDto(carWithId)).thenReturn(carResponseDto);


        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(carRequestDto)
                .when()
                .put("/cars/1")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("year", Matchers.equalTo(1234))
                .body("brand", Matchers.equalTo("brand"))
                .body("model", Matchers.equalTo("model"))
                .body("serialNumber", Matchers.equalTo("serial number"));
    }
}
