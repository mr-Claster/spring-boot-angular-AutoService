package springboot.autoservice.controller;

import springboot.autoservice.dto.request.GoodsRequestDto;
import springboot.autoservice.dto.response.GoodsResponseDto;
import springboot.autoservice.model.Goods;
import springboot.autoservice.service.GoodsService;
import springboot.autoservice.dto.mapper.GoodsMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    private final GoodsService goodsService;
    private final GoodsMapper goodsMapper;

    public GoodsController(GoodsService goodsService, GoodsMapper goodsMapper) {
        this.goodsService = goodsService;
        this.goodsMapper = goodsMapper;
    }

    @PutMapping("/{id}")
    public GoodsResponseDto update(@PathVariable Long id,
                                        @RequestBody GoodsRequestDto goodsRequestDto) {
        Goods goods = goodsMapper.mapToModel(goodsRequestDto);
        goods.setId(id);
        return goodsMapper.mapToDto(goodsService.create(goods));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GoodsResponseDto create(@RequestBody GoodsRequestDto goodsRequestDto) {
        return goodsMapper.mapToDto(goodsService.create(
                goodsMapper.mapToModel(goodsRequestDto)));
    }
}
