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
import springboot.autoservice.dto.mapper.GoodsMapper;
import springboot.autoservice.dto.request.GoodsRequestDto;
import springboot.autoservice.dto.response.GoodsResponseDto;
import springboot.autoservice.model.Goods;
import springboot.autoservice.service.GoodsService;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GoodControllerTest {
    @MockBean
    private GoodsService goodsService;
    @MockBean
    private GoodsMapper goodsMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldUpdateGoods() {
        GoodsRequestDto goodsRequestDto = new GoodsRequestDto();
        goodsRequestDto.setPrice(BigDecimal.valueOf(20));
        goodsRequestDto.setName("name");

        Goods goods = new Goods();
        goods.setPrice(BigDecimal.valueOf(20));
        goods.setName("name");
        Mockito.when(goodsMapper.mapToModel(goodsRequestDto)).thenReturn(goods);

        Goods goodsWithId = new Goods();
        goodsWithId.setId(1L);
        goodsWithId.setPrice(BigDecimal.valueOf(20));
        goodsWithId.setName("name");
        Mockito.when(goodsService.create(goodsWithId)).thenReturn(goodsWithId);

        GoodsResponseDto goodsResponseDto = new GoodsResponseDto();
        goodsResponseDto.setId(1L);
        goodsResponseDto.setPrice(BigDecimal.valueOf(20));
        goodsResponseDto.setName("name");
        Mockito.when(goodsMapper.mapToDto(goodsWithId)).thenReturn(goodsResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(goodsRequestDto)
                .when()
                .put("/goods/1")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("price", Matchers.equalTo(20))
                .body("name", Matchers.equalTo("name"));
    }

    @Test
    void shouldCreateGoods() {
        GoodsRequestDto goodsRequestDto = new GoodsRequestDto();
        goodsRequestDto.setPrice(BigDecimal.valueOf(20));
        goodsRequestDto.setName("name");

        Goods goods = new Goods();
        goods.setPrice(BigDecimal.valueOf(20));
        goods.setName("name");
        Mockito.when(goodsMapper.mapToModel(goodsRequestDto)).thenReturn(goods);

        Goods goodsWithId = new Goods();
        goodsWithId.setId(1L);
        goodsWithId.setPrice(BigDecimal.valueOf(20));
        goodsWithId.setName("name");
        Mockito.when(goodsService.create(goods)).thenReturn(goodsWithId);

        GoodsResponseDto goodsResponseDto = new GoodsResponseDto();
        goodsResponseDto.setId(1L);
        goodsResponseDto.setPrice(BigDecimal.valueOf(20));
        goodsResponseDto.setName("name");
        Mockito.when(goodsMapper.mapToDto(goodsWithId)).thenReturn(goodsResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(goodsRequestDto)
                .when()
                .post("/goods")
                .then()
                .statusCode(201)
                .body("id", Matchers.equalTo(1))
                .body("price", Matchers.equalTo(20))
                .body("name", Matchers.equalTo("name"));
    }
}
