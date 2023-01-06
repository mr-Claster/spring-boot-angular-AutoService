package springboot.autoservice.dto.mapper;

import springboot.autoservice.dto.request.GoodsRequestDto;
import springboot.autoservice.dto.response.GoodsResponseDto;
import springboot.autoservice.model.Goods;
import org.springframework.stereotype.Component;

@Component
public class GoodsMapper {
    public Goods mapToModel(GoodsRequestDto goodsRequestDto) {
        Goods good = new Goods();
        good.setName(goodsRequestDto.getName());
        good.setPrice(goodsRequestDto.getPrice());
        return good;
    }

    public GoodsResponseDto mapToDto(Goods good) {
        GoodsResponseDto goodsResponseDto = new GoodsResponseDto();
        goodsResponseDto.setId(good.getId());
        goodsResponseDto.setPrice(good.getPrice());
        goodsResponseDto.setName(good.getName());
        return goodsResponseDto;
    }
}
