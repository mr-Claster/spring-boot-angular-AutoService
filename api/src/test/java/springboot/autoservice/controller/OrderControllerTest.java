package springboot.autoservice.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
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
import springboot.autoservice.dto.mapper.FavorMapper;
import springboot.autoservice.dto.mapper.GoodsMapper;
import springboot.autoservice.dto.mapper.OrderMapper;
import springboot.autoservice.dto.request.FavorRequestDto;
import springboot.autoservice.dto.request.GoodsRequestDto;
import springboot.autoservice.dto.request.OrderRequestDto;
import springboot.autoservice.dto.response.FavorResponseDto;
import springboot.autoservice.dto.response.GoodsResponseDto;
import springboot.autoservice.dto.response.OrderResponseDto;
import springboot.autoservice.model.Car;
import springboot.autoservice.model.Favor;
import springboot.autoservice.model.Goods;
import springboot.autoservice.model.Order;
import springboot.autoservice.model.Owner;
import springboot.autoservice.service.CarService;
import springboot.autoservice.service.FavorService;
import springboot.autoservice.service.GoodsService;
import springboot.autoservice.service.OrderService;
import springboot.autoservice.service.OwnerService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @MockBean
    private OrderMapper orderMapper;
    @MockBean
    private OrderService orderService;
    @MockBean
    private GoodsMapper goodsMapper;
    @MockBean
    private GoodsService goodsService;
    @MockBean
    private OwnerService ownerService;
    @MockBean
    private FavorMapper favorMapper;
    @MockBean
    private FavorService favorService;
    @MockBean
    private CarService carService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateOrder() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setCarId(1L);
        orderRequestDto.setProblemDescription("problem description");
        orderRequestDto.setAcceptanceDate(LocalDateTime.MIN);

        Owner carOwner = new Owner();
        carOwner.setFirstName("first name");

        Car car = new Car();
        car.setId(1L);
        car.setOwner(carOwner);

        Order order = new Order();
        order.setCar(car);
        order.setProblemDescription("problem description");
        order.setAcceptanceDate(LocalDateTime.MIN);
        Mockito.when(orderMapper.mapToModel(orderRequestDto)).thenReturn(order);

        Order orderWithId = new Order();
        orderWithId.setId(2L);
        orderWithId.setCar(car);
        orderWithId.setProblemDescription("problem description");
        orderWithId.setAcceptanceDate(LocalDateTime.MIN);
        Mockito.when(orderService.create(order)).thenReturn(orderWithId);

        Mockito.when(carService.getById(orderRequestDto.getCarId())).thenReturn(car);

        Owner carOwnerWithId = new Owner();
        carOwnerWithId.setId(21L);
        carOwnerWithId.setFirstName("first name");
        Mockito.when(ownerService.create(carOwner)).thenReturn(carOwnerWithId);

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(2L);
        orderResponseDto.setProblemDescription("problem description");
        orderResponseDto.setAcceptanceDate(LocalDateTime.MIN);
        orderResponseDto.setCarId(1L);
        Mockito.when(orderMapper.mapToDto(orderWithId)).thenReturn(orderResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(orderRequestDto)
                .when()
                .post("/orders")
                .then()
                .statusCode(201)
                .body("id", Matchers.equalTo(2))
                .body("problemDescription", Matchers.equalTo("problem description"))
                .body("acceptanceDate.toString()", Matchers.equalTo("[-999999999, 1, 1, 0, 0]"))
                .body("carId", Matchers.equalTo(1));
    }

    @Test
    void shouldAddGoodsToOrder() {
        GoodsRequestDto goodsRequestDto = new GoodsRequestDto();
        goodsRequestDto.setName("name");
        goodsRequestDto.setPrice(BigDecimal.valueOf(10));

        Goods goods = new Goods();
        goods.setName("name");
        goods.setPrice(BigDecimal.valueOf(10));
        Mockito.when(goodsMapper.mapToModel(goodsRequestDto)).thenReturn(goods);

        Goods goodsWithId = new Goods();
        goodsWithId.setName("name");
        goodsWithId.setPrice(BigDecimal.valueOf(10));
        goodsWithId.setId(1L);
        Mockito.when(goodsService.create(goods)).thenReturn(goodsWithId);

        Order orderWithId = new Order();
        orderWithId.setId(2L);
        orderWithId.setProblemDescription("problem description");
        orderWithId.setAcceptanceDate(LocalDateTime.MIN);
        Mockito.when(orderService.getById(2L)).thenReturn(orderWithId);

        Order orderWithGoods = new Order();
        orderWithGoods.setId(2L);
        orderWithGoods.setProblemDescription("problem description");
        orderWithGoods.setAcceptanceDate(LocalDateTime.MIN);
        orderWithGoods.getGoods().add(goodsWithId);
        Mockito.when(orderService.create(orderWithGoods)).thenReturn(orderWithGoods);

        GoodsResponseDto goodsResponseDto = new GoodsResponseDto();
        goodsResponseDto.setId(1L);
        goodsResponseDto.setPrice(BigDecimal.valueOf(10));
        goodsResponseDto.setName("name");

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setGoods(List.of(goodsResponseDto));
        orderResponseDto.setId(2L);
        orderResponseDto.setProblemDescription("problem description");
        orderResponseDto.setAcceptanceDate(LocalDateTime.MIN);
        Mockito.when(orderMapper.mapToDto(orderWithGoods)).thenReturn(orderResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(goodsRequestDto)
                .when()
                .post("/orders/2/goods")
                .then()
                .statusCode(201)
                .body("id", Matchers.equalTo(2))
                .body("problemDescription", Matchers.equalTo("problem description"))
                .body("acceptanceDate.toString()", Matchers.equalTo("[-999999999, 1, 1, 0, 0]"))
                .body("goods[0].id", Matchers.equalTo(1))
                .body("goods[0].price", Matchers.equalTo(10))
                .body("goods[0].name", Matchers.equalTo("name"));
    }

    @Test
    void shouldAddFavorToOrder() {
        FavorRequestDto favorRequestDto = new FavorRequestDto();
        favorRequestDto.setFavorName("name");
        favorRequestDto.setPrice(BigDecimal.valueOf(10));

        Favor favor = new Favor();
        favor.setFavorName("name");
        favor.setPrice(BigDecimal.valueOf(10));
        Mockito.when(favorMapper.mapToModel(favorRequestDto)).thenReturn(favor);

        Favor favorWithId = new Favor();
        favorWithId.setFavorName("name");
        favorWithId.setPrice(BigDecimal.valueOf(10));
        favorWithId.setId(1L);
        Mockito.when(favorService.create(favor)).thenReturn(favorWithId);

        Order orderWithId = new Order();
        orderWithId.setId(2L);
        orderWithId.setProblemDescription("problem description");
        orderWithId.setAcceptanceDate(LocalDateTime.MIN);
        Mockito.when(orderService.getById(2L)).thenReturn(orderWithId);

        Order orderWithGoods = new Order();
        orderWithGoods.setId(2L);
        orderWithGoods.setProblemDescription("problem description");
        orderWithGoods.setAcceptanceDate(LocalDateTime.MIN);
        orderWithGoods.getFavors().add(favorWithId);
        Mockito.when(orderService.create(orderWithGoods)).thenReturn(orderWithGoods);

        FavorResponseDto favorResponseDto = new FavorResponseDto();
        favorResponseDto.setId(1L);
        favorResponseDto.setPrice(BigDecimal.valueOf(10));
        favorResponseDto.setFavorName("name");

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setFavors(List.of(favorResponseDto));
        orderResponseDto.setId(2L);
        orderResponseDto.setProblemDescription("problem description");
        orderResponseDto.setAcceptanceDate(LocalDateTime.MIN);
        Mockito.when(orderMapper.mapToDto(orderWithGoods)).thenReturn(orderResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(favorRequestDto)
                .when()
                .post("/orders/2/favors")
                .then()
                .statusCode(201)
                .body("id", Matchers.equalTo(2))
                .body("problemDescription", Matchers.equalTo("problem description"))
                .body("acceptanceDate.toString()", Matchers.equalTo("[-999999999, 1, 1, 0, 0]"))
                .body("favors[0].id", Matchers.equalTo(1))
                .body("favors[0].price", Matchers.equalTo(10))
                .body("favors[0].favorName", Matchers.equalTo("name"));
    }

    @Test
    void shouldUpdateOrder() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setAcceptanceDate(LocalDateTime.MIN);
        orderRequestDto.setCarId(1L);
        orderRequestDto.setProblemDescription("problem description");

        Car car = new Car();
        car.setId(1L);

        Order order = new Order();
        order.setAcceptanceDate(LocalDateTime.MIN);
        order.setProblemDescription("problem description");
        order.setCar(car);
        Mockito.when(orderMapper.mapToModel(orderRequestDto)).thenReturn(order);

        Order orderWithId = new Order();
        orderWithId.setId(2L);
        orderWithId.setAcceptanceDate(LocalDateTime.MIN);
        orderWithId.setProblemDescription("problem description");
        orderWithId.setCar(car);

        Mockito.when(orderService.create(orderWithId)).thenReturn(orderWithId);


        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(2L);
        orderResponseDto.setAcceptanceDate(LocalDateTime.MIN);
        orderResponseDto.setProblemDescription("problem description");
        orderResponseDto.setCarId(1L);
        Mockito.when(orderMapper.mapToDto(orderWithId)).thenReturn(orderResponseDto);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(orderResponseDto)
                .when()
                .put("/orders/2")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(2))
                .body("problemDescription", Matchers.equalTo("problem description"))
                .body("acceptanceDate.toString()", Matchers.equalTo("[-999999999, 1, 1, 0, 0]"))
                .body("carId", Matchers.equalTo(1));
    }

    @Test
    void shouldUpdateOrderStatus() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(Order.Status.COMPLETED);

        Mockito.when(orderService.changeStatus(1L, Order.Status.valueOf("COMPLETED")))
                .thenReturn(order);

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(1L);
        orderResponseDto.setStatus("COMPLETED");

        Mockito.when(orderMapper.mapToDto(order)).thenReturn(orderResponseDto);
        RestAssuredMockMvc.given()
                .put("/orders/1/status?newStatus=COMPLETED")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("status", Matchers.equalTo("COMPLETED"));
    }

    @Test
    void shouldShowOrderPrice() {
        Mockito.when(orderService.setFinalPrice(1L, 10)).thenReturn(BigDecimal.valueOf(100));
        RestAssuredMockMvc.when()
                .get("/orders/1/price?bonus=10")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("100"));
    }
}
