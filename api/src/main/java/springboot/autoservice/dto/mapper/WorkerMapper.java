package springboot.autoservice.dto.mapper;

import springboot.autoservice.dto.request.WorkerRequestDto;
import springboot.autoservice.dto.response.WorkerResponseDto;
import springboot.autoservice.model.Worker;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class WorkerMapper {
    private final OrderMapper orderMapper;

    public WorkerMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public Worker mapToModel(WorkerRequestDto workerRequestDto) {
        Worker worker = new Worker();
        worker.setFirstName(workerRequestDto.getFirstName());
        worker.setLastName(workerRequestDto.getLastName());
        return worker;
    }

    public WorkerResponseDto mapToDto(Worker worker) {
        WorkerResponseDto workerResponseDto = new WorkerResponseDto();
        workerResponseDto.setId(worker.getId());
        workerResponseDto.setFirstName(worker.getFirstName());
        workerResponseDto.setLastName(worker.getLastName());
        workerResponseDto.setCompletedOrders(worker.getCompletedOrders().stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList()));
        return workerResponseDto;
    }
}
