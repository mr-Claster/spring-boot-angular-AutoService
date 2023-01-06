package springboot.autoservice.dto.mapper;

import springboot.autoservice.dto.request.FavorRequestDto;
import springboot.autoservice.dto.response.FavorResponseDto;
import springboot.autoservice.model.Favor;
import springboot.autoservice.service.OrderService;
import springboot.autoservice.service.WorkerService;
import org.springframework.stereotype.Component;

@Component
public class FavorMapper {
    private final WorkerService workerService;

    public FavorMapper(WorkerService workerService) {
        this.workerService = workerService;
    }

    public Favor mapToModel(FavorRequestDto favorRequestDto) {
        Favor favor = new Favor(Favor.Status.NOT_PAID);
        favor.setFavorName(favorRequestDto.getFavorName());
        favor.setWorker(workerService.getById(favorRequestDto.getWorkerId()));
        favor.setPrice(favorRequestDto.getPrice());
        return favor;
    }

    public FavorResponseDto mapToDto(Favor favor) {
        FavorResponseDto favorResponseDto = new FavorResponseDto();
        favorResponseDto.setId(favor.getId());
        favorResponseDto.setFavorName(favor.getFavorName());
        favorResponseDto.setPrice(favor.getPrice());
        favorResponseDto.setWorkerId(favor.getWorker().getId());
        favorResponseDto.setStatus(favor.getStatus().name());
        return favorResponseDto;
    }
}
