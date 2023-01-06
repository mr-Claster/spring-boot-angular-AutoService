package springboot.autoservice.dto.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springboot.autoservice.dto.request.GoodsRequestDto;
import springboot.autoservice.dto.response.GoodsResponseDto;
import springboot.autoservice.model.Goods;
import java.math.BigDecimal;

@SpringBootTest
class GoodsMapperTest {
    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    void shouldReturnGoodResponseDto() {
        Goods good = new Goods();
        good.setId(1L);
        good.setPrice(BigDecimal.valueOf(100));
        good.setName("name");

        GoodsResponseDto actual = goodsMapper.mapToDto(good);

        GoodsResponseDto expected = new GoodsResponseDto();
        expected.setId(1L);
        expected.setPrice(BigDecimal.valueOf(100));
        expected.setName("name");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldReturnGood() {
        GoodsRequestDto goodsRequestDto = new GoodsRequestDto();
        goodsRequestDto.setName("name");
        goodsRequestDto.setPrice(BigDecimal.valueOf(100));

        Goods actual = goodsMapper.mapToModel(goodsRequestDto);

        Goods expected = new Goods();
        expected.setName("name");
        expected.setPrice(BigDecimal.valueOf(100));

        Assertions.assertEquals(expected, actual);
    }
}
