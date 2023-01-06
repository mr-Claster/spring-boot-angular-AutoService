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
import springboot.autoservice.dto.mapper.FavorMapper;
import springboot.autoservice.dto.request.FavorRequestDto;
import springboot.autoservice.dto.response.FavorResponseDto;
import springboot.autoservice.model.Favor;
import springboot.autoservice.model.Worker;
import springboot.autoservice.service.FavorService;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FavorControllerTest {
    @MockBean
    private FavorService favorService;
    @MockBean
    private FavorMapper favorMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldUpdateFavor() {
        FavorRequestDto favorRequestDto = new FavorRequestDto();
        favorRequestDto.setFavorName("name");
        favorRequestDto.setPrice(BigDecimal.valueOf(200));
        favorRequestDto.setWorkerId(1L);

        Worker worker = new Worker();
        worker.setId(1L);

        Favor favor = new Favor();
        favor.setFavorName("name");
        favor.setPrice(BigDecimal.valueOf(200));
        favor.setWorker(worker);
        Mockito.when(favorMapper.mapToModel(favorRequestDto)).thenReturn(favor);

        Favor favorWithId = new Favor();
        favorWithId.setId(2L);
        favorWithId.setFavorName("name");
        favorWithId.setPrice(BigDecimal.valueOf(200));
        favorWithId.setWorker(worker);
        Mockito.when(favorService.create(favorWithId)).thenReturn(favorWithId);

        FavorResponseDto favorResponseDto = new FavorResponseDto();
        favorResponseDto.setId(2L);
        favorResponseDto.setFavorName("name");
        favorResponseDto.setPrice(BigDecimal.valueOf(200));
        favorResponseDto.setWorkerId(1L);
        Mockito.when(favorMapper.mapToDto(favorWithId)).thenReturn(favorResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(favorRequestDto)
                .when()
                .put("/favors/2")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(2))
                .body("favorName", Matchers.equalTo("name"))
                .body("price", Matchers.equalTo(200))
                .body("workerId", Matchers.equalTo(1));
    }

    @Test
    void shouldUpdateFavorStatus() {
        Favor favor = new Favor();
        favor.setStatus(Favor.Status.PAID);
        favor.setId(1L);
        Mockito.when(favorService.changeStatus(1L, Favor.Status.PAID))
                .thenReturn(favor);

        FavorResponseDto favorResponseDto = new FavorResponseDto();
        favorResponseDto.setId(1L);
        favorResponseDto.setStatus("PAID");
        Mockito.when(favorMapper.mapToDto(favor)).thenReturn(favorResponseDto);

        RestAssuredMockMvc.given()
                .put("/favors/1/status?newStatus=PAID")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("status", Matchers.equalTo("PAID"));
    }

    @Test
    void shouldCreateFavor() {
        FavorRequestDto favorRequestDto = new FavorRequestDto();
        favorRequestDto.setFavorName("name");
        favorRequestDto.setPrice(BigDecimal.valueOf(200));
        favorRequestDto.setWorkerId(1L);

        Worker worker = new Worker();
        worker.setId(1L);

        Favor favor = new Favor();
        favor.setFavorName("name");
        favor.setPrice(BigDecimal.valueOf(200));
        favor.setWorker(worker);
        Mockito.when(favorMapper.mapToModel(favorRequestDto)).thenReturn(favor);

        Favor favorWithId = new Favor();
        favorWithId.setId(2L);
        favorWithId.setFavorName("name");
        favorWithId.setPrice(BigDecimal.valueOf(200));
        favorWithId.setWorker(worker);
        Mockito.when(favorService.create(favor)).thenReturn(favorWithId);

        FavorResponseDto favorResponseDto = new FavorResponseDto();
        favorResponseDto.setId(2L);
        favorResponseDto.setFavorName("name");
        favorResponseDto.setPrice(BigDecimal.valueOf(200));
        favorResponseDto.setWorkerId(1L);
        Mockito.when(favorMapper.mapToDto(favorWithId)).thenReturn(favorResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(favorRequestDto)
                .when()
                .post("/favors")
                .then()
                .statusCode(201)
                .body("id", Matchers.equalTo(2))
                .body("favorName", Matchers.equalTo("name"))
                .body("price", Matchers.equalTo(200))
                .body("workerId", Matchers.equalTo(1));
    }
}
