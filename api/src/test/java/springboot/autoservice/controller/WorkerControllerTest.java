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
import springboot.autoservice.dto.mapper.WorkerMapper;
import springboot.autoservice.dto.request.WorkerRequestDto;
import springboot.autoservice.dto.response.OrderResponseDto;
import springboot.autoservice.dto.response.WorkerResponseDto;
import springboot.autoservice.model.Order;
import springboot.autoservice.model.Worker;
import springboot.autoservice.service.SalaryService;
import springboot.autoservice.service.WorkerService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class WorkerControllerTest {
    @MockBean
    private SalaryService salaryService;
    @MockBean
    private WorkerMapper workerMapper;
    @MockBean
    private WorkerService workerService;
    @MockBean
    private OrderMapper orderMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateWorker() {
        WorkerRequestDto workerRequestDto = new WorkerRequestDto();
        workerRequestDto.setFirstName("first name");
        workerRequestDto.setLastName("last name");

        Worker worker = new Worker();
        worker.setFirstName("first name");
        worker.setLastName("last name");
        Mockito.when(workerMapper.mapToModel(workerRequestDto)).thenReturn(worker);

        Worker workerWithId = new Worker();
        workerWithId.setId(1L);
        workerWithId.setFirstName("first name");
        workerWithId.setLastName("last name");
        Mockito.when(workerService.create(worker)).thenReturn(workerWithId);

        WorkerResponseDto workerResponseDto = new WorkerResponseDto();
        workerResponseDto.setId(1L);
        workerResponseDto.setFirstName("first name");
        workerResponseDto.setLastName("last name");
        Mockito.when(workerMapper.mapToDto(workerWithId)).thenReturn(workerResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(workerResponseDto)
                .when()
                .post("/workers")
                .then()
                .statusCode(201)
                .body("id", Matchers.equalTo(1))
                .body("firstName", Matchers.equalTo("first name"))
                .body("lastName", Matchers.equalTo("last name"));
    }

    @Test
    void shouldUpdateWorker() {
        WorkerRequestDto workerRequestDto = new WorkerRequestDto();
        workerRequestDto.setFirstName("first name");
        workerRequestDto.setLastName("last name");

        Worker worker = new Worker();
        worker.setFirstName("first name");
        worker.setLastName("last name");
        Mockito.when(workerMapper.mapToModel(workerRequestDto)).thenReturn(worker);

        Worker workerWithId = new Worker();
        workerWithId.setId(1L);
        workerWithId.setFirstName("first name");
        workerWithId.setLastName("last name");
        Mockito.when(workerService.create(workerWithId)).thenReturn(workerWithId);

        WorkerResponseDto workerResponseDto = new WorkerResponseDto();
        workerResponseDto.setId(1L);
        workerResponseDto.setFirstName("first name");
        workerResponseDto.setLastName("last name");
        Mockito.when(workerMapper.mapToDto(workerWithId)).thenReturn(workerResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(workerResponseDto)
                .put("/workers/1")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("firstName", Matchers.equalTo("first name"))
                .body("lastName", Matchers.equalTo("last name"));
    }

    @Test
    void shouldCompleteOrdersByWorkerId() {
        Order order1 = new Order();
        order1.setId(1L);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        Order order2 = new Order();
        order2.setId(2L);
        orderList.add(order2);

        Mockito.when(workerService.getAllCompletedOrdersById(1L)).thenReturn(orderList);
        OrderResponseDto orderMock1 = new OrderResponseDto();
        orderMock1.setId(1L);
        Mockito.when(orderMapper.mapToDto(order1)).thenReturn(orderMock1);
        OrderResponseDto orderMock2 = new OrderResponseDto();
        orderMock2.setId(2L);
        Mockito.when(orderMapper.mapToDto(order2)).thenReturn(orderMock2);

        RestAssuredMockMvc.when()
                .get("/workers/1/orders")
                .then()
                .statusCode(200)
                .body("[0].id", Matchers.equalTo(1))
                .body("[1].id", Matchers.equalTo(2));
    }

    @Test
    void shouldShowWorkerSalaryByOrderId() {
        Mockito.when(salaryService.getSalaryByWorkerIdAndOrderId(1L,1L))
                .thenReturn(BigDecimal.valueOf(20));
        RestAssuredMockMvc.when()
                .get("/workers/1/orders/1/salary")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("20"));
    }
}
