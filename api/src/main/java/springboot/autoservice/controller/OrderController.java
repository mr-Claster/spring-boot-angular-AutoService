package springboot.autoservice.controller;

import springboot.autoservice.dto.request.FavorRequestDto;
import springboot.autoservice.dto.request.GoodsRequestDto;
import springboot.autoservice.dto.request.OrderRequestDto;
import springboot.autoservice.dto.response.OrderResponseDto;
import springboot.autoservice.model.Favor;
import springboot.autoservice.model.Goods;
import springboot.autoservice.model.Order;
import springboot.autoservice.model.Owner;
import springboot.autoservice.service.CarService;
import springboot.autoservice.service.FavorService;
import springboot.autoservice.service.GoodsService;
import springboot.autoservice.service.OrderService;
import springboot.autoservice.service.OwnerService;
import springboot.autoservice.dto.mapper.FavorMapper;
import springboot.autoservice.dto.mapper.GoodsMapper;
import springboot.autoservice.dto.mapper.OrderMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;


@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final GoodsMapper goodsMapper;
    private final GoodsService goodsService;
    private final OwnerService ownerService;
    private final FavorMapper favorMapper;
    private final FavorService favorService;
    private final CarService carService;

    public OrderController(OrderMapper orderMapper,
                           OrderService orderService,
                           GoodsMapper goodsMapper,
                           GoodsService goodsService,
                           OwnerService ownerService,
                           FavorMapper favorMapper,
                           FavorService favorService,
                           CarService carService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
        this.goodsMapper = goodsMapper;
        this.goodsService = goodsService;
        this.ownerService = ownerService;
        this.favorMapper = favorMapper;
        this.favorService = favorService;
        this.carService = carService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto create(@RequestBody OrderRequestDto orderRequestDto) {
        Order order = orderMapper.mapToModel(orderRequestDto);
        order = orderService.create(order);
        Owner owner = carService
                .getById(orderRequestDto.getCarId()).getOwner();
        owner.getOrders().add(order);
        owner = ownerService.create(owner);
        return orderMapper.mapToDto(order);
    }

    @PostMapping("/{id}/goods")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto addGoods(@PathVariable Long id,
                                     @RequestBody GoodsRequestDto goodsRequestDto) {
        Goods good = goodsMapper.mapToModel(goodsRequestDto);
        good = goodsService.create(good);
        Order order = orderService.getById(id);
        order.getGoods().add(good);
        return orderMapper
                .mapToDto(orderService.create(order));
    }

    @PostMapping("/{id}/favors")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto addFavor(@PathVariable Long id,
                                     @RequestBody FavorRequestDto favorRequestDto) {
        Favor favor = favorMapper.mapToModel(favorRequestDto);
        favor = favorService.create(favor);
        Order order = orderService.getById(id);
        order.getFavors().add(favor);
        return orderMapper
                .mapToDto(orderService.create(order));
    }

    @PutMapping("/{id}")
    public OrderResponseDto update(@PathVariable Long id,
                                      @RequestBody OrderRequestDto orderRequestDto) {
        Order order = orderMapper.mapToModel(orderRequestDto);
        order.setId(id);
        return orderMapper.mapToDto(orderService.create(order));
    }

    @PutMapping("/{id}/status")
    public OrderResponseDto updateStatus(@PathVariable Long id,
                                              @RequestParam String newStatus) {
        return orderMapper
                .mapToDto(orderService.changeStatus(id, Order.Status.valueOf(newStatus)));
    }

    @GetMapping("/{id}/price")
    public BigDecimal getPrice(@PathVariable Long id, @RequestParam Integer bonus) {
        return orderService.setFinalPrice(id, bonus);
    }
}
