package springboot.autoservice.controller;

import springboot.autoservice.dto.request.WorkerRequestDto;
import springboot.autoservice.dto.response.OrderResponseDto;
import springboot.autoservice.dto.response.WorkerResponseDto;
import springboot.autoservice.model.Worker;
import springboot.autoservice.service.WorkerService;
import springboot.autoservice.service.SalaryService;
import springboot.autoservice.dto.mapper.OrderMapper;
import springboot.autoservice.dto.mapper.WorkerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workers")
public class WorkerController {
    private final WorkerService workerService;
    private final WorkerMapper workerMapper;
    private final OrderMapper orderMapper;
    private final SalaryService salaryService;

    public WorkerController(WorkerService workerService,
                            WorkerMapper workerMapper,
                            OrderMapper orderMapper,
                            SalaryService salaryService) {
        this.workerService = workerService;
        this.workerMapper = workerMapper;
        this.orderMapper = orderMapper;
        this.salaryService = salaryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkerResponseDto create(@RequestBody
                                             WorkerRequestDto workerRequestDto) {
        return workerMapper
                .mapToDto(workerService
                        .create(workerMapper
                                .mapToModel(workerRequestDto)));
    }

    @PutMapping("/{id}")
    public WorkerResponseDto update(@PathVariable Long id,
                                             @RequestBody WorkerRequestDto workerRequestDto) {
        Worker worker = workerMapper.mapToModel(workerRequestDto);
        worker.setId(id);
        return workerMapper.mapToDto(workerService.create(worker));
    }

    @GetMapping("/{id}/orders")
    public List<OrderResponseDto> getByCompletedOrdersById(@PathVariable Long id) {
        return workerService.getAllCompletedOrdersById(id).stream()
                .distinct()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{workerId}/orders/{orderId}/salary")
    public BigDecimal getWorkerSalaryByOrderId(@PathVariable("workerId") Long workerId,
                                                  @PathVariable("orderId") Long orderId) {
        return salaryService.getSalaryByWorkerIdAndOrderId(workerId, orderId);
    }
}
