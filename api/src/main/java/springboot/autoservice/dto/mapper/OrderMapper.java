package springboot.autoservice.dto.mapper;

import springboot.autoservice.dto.request.OrderRequestDto;
import springboot.autoservice.dto.response.OrderResponseDto;
import springboot.autoservice.model.Order;
import springboot.autoservice.service.CarService;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final CarService carService;
    private final GoodsMapper goodsMapper;
    private final FavorMapper favorMapper;

    public OrderMapper(CarService carService,
                       GoodsMapper goodsMapper,
                       FavorMapper favorMapper) {
        this.carService = carService;
        this.goodsMapper = goodsMapper;
        this.favorMapper = favorMapper;
    }

    public Order mapToModel(OrderRequestDto orderRequestDto) {
        Order order = new Order(Order.Status.ACCEPTED);
        order.setCar(carService.getById(orderRequestDto.getCarId()));
        order.setAcceptanceDate(orderRequestDto.getAcceptanceDate());
        order.setProblemDescription(orderRequestDto.getProblemDescription());
        return order;
    }

    public OrderResponseDto mapToDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setCarId(order.getCar().getId());
        dto.setGoods(order.getGoods().stream()
                .map(goodsMapper::mapToDto)
                .collect(Collectors.toList()));
        dto.setFavors(order.getFavors().stream()
                .map(favorMapper::mapToDto)
                .collect(Collectors.toList()));
        dto.setAcceptanceDate(order.getAcceptanceDate());
        dto.setFinalPrice(order.getFinalPrice());
        dto.setEndDate(order.getEndDate());
        dto.setProblemDescription(order.getProblemDescription());
        dto.setStatus(order.getStatus().name());
        return dto;
    }
}
