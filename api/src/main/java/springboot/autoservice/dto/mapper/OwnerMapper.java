package springboot.autoservice.dto.mapper;

import springboot.autoservice.dto.request.OwnerRequestDto;
import springboot.autoservice.dto.response.OwnerResponseDto;
import springboot.autoservice.model.Owner;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OwnerMapper {
    private final OrderMapper orderMapper;
    public OwnerMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public Owner mapToModel(OwnerRequestDto ownerRequestDto) {
        Owner owner = new Owner();
        owner.setFirstName(ownerRequestDto.getFirstName());
        owner.setLastName(ownerRequestDto.getLastName());
        return owner;
    }

    public OwnerResponseDto mapToDto(Owner owner) {
        OwnerResponseDto ownerResponseDto = new OwnerResponseDto();
        ownerResponseDto.setId(owner.getId());
        ownerResponseDto.setFirstName(owner.getFirstName());
        ownerResponseDto.setLastName(owner.getLastName());
        ownerResponseDto.setOrders(owner.getOrders().stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList()));
        return ownerResponseDto;
    }
}
