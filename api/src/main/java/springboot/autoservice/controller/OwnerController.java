package springboot.autoservice.controller;

import springboot.autoservice.dto.request.OwnerRequestDto;
import springboot.autoservice.dto.response.OrderResponseDto;
import springboot.autoservice.dto.response.OwnerResponseDto;
import springboot.autoservice.model.Owner;
import springboot.autoservice.service.OwnerService;
import springboot.autoservice.dto.mapper.OrderMapper;
import springboot.autoservice.dto.mapper.OwnerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OrderMapper orderMapper;
    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    public OwnerController(OrderMapper orderMapper,
                           OwnerService ownerService,
                           OwnerMapper ownerMapper) {
        this.orderMapper = orderMapper;
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
    }

    @GetMapping("/{id}/orders")
    public List<OrderResponseDto> getAllOrdersById(@PathVariable Long id) {
        return ownerService.getById(id).getOrders().stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerResponseDto create(@RequestBody OwnerRequestDto ownerRequestDto) {
        return ownerMapper
                .mapToDto(ownerService
                        .create(ownerMapper.mapToModel(ownerRequestDto)));
    }

    @PutMapping("/{id}")
    public OwnerResponseDto update(@PathVariable Long id,
                                        @RequestBody OwnerRequestDto ownerRequestDto) {
        Owner owner = ownerMapper.mapToModel(ownerRequestDto);
        owner.setId(id);
        return ownerMapper.mapToDto(ownerService.create(owner));
    }
}
