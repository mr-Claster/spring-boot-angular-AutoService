package springboot.autoservice.dto.mapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import springboot.autoservice.dto.request.CarRequestDto;
import springboot.autoservice.dto.response.CarResponseDto;
import springboot.autoservice.dto.response.OrderResponseDto;
import springboot.autoservice.dto.response.OwnerResponseDto;
import springboot.autoservice.model.Car;
import springboot.autoservice.model.Order;
import springboot.autoservice.model.Owner;
import springboot.autoservice.service.OwnerService;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class CarMapperTest {
    @Autowired
    private CarMapper carMapper;
    @MockBean
    private OwnerMapper ownerMapper;
    @MockBean
    private OwnerService ownerService;

    @Test
    void shouldReturnCarResponseDto() {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setFirstName("Bob");
        owner.setLastName("Smith");

        Car car = new Car();
        car.setId(1L);
        car.setBrand("Toyota");
        car.setModel("Supra");
        car.setOwner(owner);
        car.setYear(2016);
        car.setSerialNumber("AS1234DS");

        Order order = new Order();
        order.setCar(car);
        order.setFavors(new ArrayList<>());
        order.setGoods(new ArrayList<>());
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        owner.setOrders(orders);

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setCarId(1L);
        orderResponseDto.setFavors(new ArrayList<>());
        orderResponseDto.setGoods(new ArrayList<>());
        List<OrderResponseDto> ordersResponse = new ArrayList<>();
        ordersResponse.add(orderResponseDto);

        OwnerResponseDto ownerResponseDto = new OwnerResponseDto();
        ownerResponseDto.setFirstName("Bob");
        ownerResponseDto.setLastName("Smith");
        ownerResponseDto.setId(1L);
        ownerResponseDto.setOrders(ordersResponse);
        Mockito.when(ownerMapper.mapToDto(owner)).thenReturn(ownerResponseDto);
        CarResponseDto actual = carMapper.mapToDto(car);

        OrderResponseDto orderExpected = new OrderResponseDto();
        orderExpected.setCarId(1L);
        orderExpected.setFavors(new ArrayList<>());
        orderExpected.setGoods(new ArrayList<>());
        List<OrderResponseDto> ordersExpected = new ArrayList<>();
        ordersExpected.add(orderResponseDto);

        OwnerResponseDto ownerExpected = new OwnerResponseDto();
        ownerExpected.setFirstName("Bob");
        ownerExpected.setLastName("Smith");
        ownerExpected.setId(1L);
        ownerExpected.setOrders(ordersResponse);

        CarResponseDto expected = new CarResponseDto();
        expected.setId(1L);
        expected.setYear(2016);
        expected.setSerialNumber("AS1234DS");
        expected.setModel("Supra");
        expected.setBrand("Toyota");
        expected.setOwnerId(1L);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldReturnCar() {
        CarRequestDto carRequestDto =  new CarRequestDto();
        carRequestDto.setYear(2016);
        carRequestDto.setBrand("Toyota");
        carRequestDto.setModel("Supra");
        carRequestDto.setSerialNumber("AS1234DS");
        carRequestDto.setOwnerId(1L);

        Owner owner = new Owner();
        owner.setId(1L);
        owner.setFirstName("Bob");
        owner.setLastName("Smith");

        Order order = new Order();
        order.setFavors(new ArrayList<>());
        order.setGoods(new ArrayList<>());
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        owner.setOrders(orders);

        Mockito.when(ownerService.getById(1L)).thenReturn(owner);
        Car actual = carMapper.mapToModel(carRequestDto);

        Owner ownerExpected = new Owner();
        ownerExpected.setId(1L);
        ownerExpected.setFirstName("Bob");
        ownerExpected.setLastName("Smith");

        Order orderExpected = new Order();
        orderExpected.setFavors(new ArrayList<>());
        orderExpected.setGoods(new ArrayList<>());
        List<Order> ordersExpected = new ArrayList<>();
        ordersExpected.add(orderExpected);
        ownerExpected.setOrders(ordersExpected);

        Car expected = new Car();
        expected.setYear(2016);
        expected.setBrand("Toyota");
        expected.setModel("Supra");
        expected.setSerialNumber("AS1234DS");
        expected.setOwner(ownerExpected);

        Assertions.assertEquals(expected, actual);
    }
}
