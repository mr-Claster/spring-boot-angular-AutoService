package springboot.autoservice.dto.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import springboot.autoservice.dto.request.OrderRequestDto;
import springboot.autoservice.dto.response.FavorResponseDto;
import springboot.autoservice.dto.response.GoodsResponseDto;
import springboot.autoservice.dto.response.OrderResponseDto;
import springboot.autoservice.model.Car;
import springboot.autoservice.model.Favor;
import springboot.autoservice.model.Goods;
import springboot.autoservice.model.Order;
import springboot.autoservice.model.Worker;
import springboot.autoservice.service.CarService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class OrderMapperTest {
    @Autowired
    private OrderMapper orderMapper;
    @MockBean
    private CarService carService;
    @MockBean
    private GoodsMapper goodsMapper;
    @MockBean
    private FavorMapper favorMapper;

    @Test
    void shouldReturnOrderResponseDto() {
        Worker worker = new Worker();
        worker.setId(1L);

        Favor favor = new Favor();
        favor.setId(2L);
        favor.setWorker(worker);
        favor.setPrice(BigDecimal.valueOf(100));
        favor.setFavorName("favor name");
        favor.setStatus(Favor.Status.PAID);
        List<Favor> favorList = new ArrayList<>();
        favorList.add(favor);

        Goods goods = new Goods();
        goods.setName("name");
        goods.setPrice(BigDecimal.valueOf(200));
        goods.setId(3L);
        List<Goods> goodsList = new ArrayList<>();
        goodsList.add(goods);

        Car car = new Car();
        car.setId(4L);

        Order order = new Order();
        order.setId(5L);
        order.setStatus(Order.Status.ACCEPTED);
        order.setFavors(favorList);
        order.setGoods(goodsList);
        order.setProblemDescription("problem description");
        order.setAcceptanceDate(LocalDateTime.MAX);
        order.setCar(car);
        order.setAcceptanceDate(LocalDateTime.MIN);
        order.setEndDate(LocalDateTime.MAX);
        order.setFinalPrice(BigDecimal.valueOf(300));

        GoodsResponseDto goodsMockito = new GoodsResponseDto();
        goodsMockito.setPrice(BigDecimal.valueOf(200));
        goodsMockito.setId(3L);
        goodsMockito.setName("name");
        Mockito.when(goodsMapper.mapToDto(goods)).thenReturn(goodsMockito);

        FavorResponseDto favorMockito = new FavorResponseDto();
        favorMockito.setId(2L);
        favorMockito.setWorkerId(1L);
        favorMockito.setFavorName("favor name");
        favorMockito.setStatus("PAID");
        favorMockito.setPrice(BigDecimal.valueOf(100));
        Mockito.when(favorMapper.mapToDto(favor)).thenReturn(favorMockito);

        OrderResponseDto actual = orderMapper.mapToDto(order);

        GoodsResponseDto goodsExpected = new GoodsResponseDto();
        goodsExpected.setPrice(BigDecimal.valueOf(200));
        goodsExpected.setId(3L);
        goodsExpected.setName("name");

        FavorResponseDto favorExpected = new FavorResponseDto();
        favorExpected.setId(2L);
        favorExpected.setWorkerId(1L);
        favorExpected.setFavorName("favor name");
        favorExpected.setStatus("PAID");
        favorExpected.setPrice(BigDecimal.valueOf(100));

        OrderResponseDto expected = new OrderResponseDto();
        expected.setId(5L);
        expected.setGoods(List.of(goodsExpected));
        expected.setFavors(List.of(favorExpected));
        expected.setCarId(4L);
        expected.setAcceptanceDate(LocalDateTime.MIN);
        expected.setEndDate(LocalDateTime.MAX);
        expected.setStatus("ACCEPTED");
        expected.setProblemDescription("problem description");
        expected.setFinalPrice(BigDecimal.valueOf(300));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldReturnOrder() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setCarId(1L);
        orderRequestDto.setAcceptanceDate(LocalDateTime.MAX);
        orderRequestDto.setProblemDescription("problem description");

        Car car = new Car();
        car.setId(1L);
        Mockito.when(carService.getById(1L)).thenReturn(car);

        Order actual = orderMapper.mapToModel(orderRequestDto);

        Car carExpected = new Car();
        carExpected.setId(1L);

        Order expected = new Order();
        expected.setCar(carExpected);
        expected.setAcceptanceDate(LocalDateTime.MAX);
        expected.setProblemDescription("problem description");
        expected.setStatus(Order.Status.ACCEPTED);

        Assertions.assertEquals(expected, actual);
    }
}
