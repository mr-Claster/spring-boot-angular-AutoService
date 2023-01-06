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
import springboot.autoservice.dto.mapper.OrderMapper;
import springboot.autoservice.dto.mapper.OwnerMapper;
import springboot.autoservice.dto.request.OwnerRequestDto;
import springboot.autoservice.dto.response.OrderResponseDto;
import springboot.autoservice.dto.response.OwnerResponseDto;
import springboot.autoservice.model.Order;
import springboot.autoservice.model.Owner;
import springboot.autoservice.service.OwnerService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OwnerControllerTest {
    @MockBean
    private OrderMapper orderMapper;
    @MockBean
    private OwnerService ownerService;
    @MockBean
    private OwnerMapper ownerMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldShowAllOrdersByOwnerId() {
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setId(2L);
        orders.add(order1);
        Order order2 = new Order();
        order2.setId(3L);
        orders.add(order2);

        Owner owner = new Owner();
        owner.setId(1L);
        owner.setFirstName("first name");
        owner.setLastName("last name");
        owner.setOrders(orders);
        Mockito.when(ownerService.getById(1L)).thenReturn(owner);

        OrderResponseDto orderDto1 = new OrderResponseDto();
        orderDto1.setId(1L);
        OrderResponseDto orderDto2 = new OrderResponseDto();
        orderDto2.setId(2L);
        Mockito.when(orderMapper.mapToDto(order1)).thenReturn(orderDto1);
        Mockito.when(orderMapper.mapToDto(order2)).thenReturn(orderDto2);

        RestAssuredMockMvc.when()
                .get("/owners/1/orders")
                .then()
                .statusCode(200)
                .body("[0].id", Matchers.equalTo(1))
                .body("[1].id", Matchers.equalTo(2));

    }

    @Test
    void shouldCreateOwner() {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setFirstName("first name");
        ownerRequestDto.setLastName("last name");

        Owner owner = new Owner();
        owner.setFirstName(ownerRequestDto.getFirstName());
        owner.setLastName(ownerRequestDto.getLastName());
        Mockito.when(ownerMapper.mapToModel(ownerRequestDto)).thenReturn(owner);

        Owner ownerWithId = new Owner();
        ownerWithId.setId(1L);
        ownerWithId.setFirstName(owner.getFirstName());
        ownerWithId.setLastName(owner.getLastName());
        Mockito.when(ownerService.create(owner)).thenReturn(ownerWithId);

        OwnerResponseDto ownerResponseDto = new OwnerResponseDto();
        ownerResponseDto.setFirstName(owner.getFirstName());
        ownerResponseDto.setLastName(owner.getLastName());
        ownerResponseDto.setId(1L);
        Mockito.when(ownerMapper.mapToDto(ownerWithId)).thenReturn(ownerResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(ownerResponseDto)
                .when()
                .post("/owners")
                .then()
                .statusCode(201)
                .body("id", Matchers.equalTo(1))
                .body("firstName", Matchers.equalTo("first name"))
                .body("lastName", Matchers.equalTo("last name"));
    }

    @Test
    void shouldUpdateOwner() {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setFirstName("first name");
        ownerRequestDto.setLastName("last name");

        Owner owner = new Owner();
        owner.setFirstName(ownerRequestDto.getFirstName());
        owner.setLastName(ownerRequestDto.getLastName());
        Mockito.when(ownerMapper.mapToModel(ownerRequestDto)).thenReturn(owner);

        Owner ownerWithId = new Owner();
        ownerWithId.setId(1L);
        ownerWithId.setFirstName(owner.getFirstName());
        ownerWithId.setLastName(owner.getLastName());
        Mockito.when(ownerService.create(ownerWithId)).thenReturn(ownerWithId);

        OwnerResponseDto ownerResponseDto = new OwnerResponseDto();
        ownerResponseDto.setFirstName(owner.getFirstName());
        ownerResponseDto.setLastName(owner.getLastName());
        ownerResponseDto.setId(1L);
        Mockito.when(ownerMapper.mapToDto(ownerWithId)).thenReturn(ownerResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(ownerResponseDto)
                .when()
                .put("/owners/1")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("firstName", Matchers.equalTo("first name"))
                .body("lastName", Matchers.equalTo("last name"));

    }
}
