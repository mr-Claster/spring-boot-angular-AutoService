package springboot.autoservice.service.impl;

import springboot.autoservice.model.Order;
import springboot.autoservice.model.Worker;
import springboot.autoservice.repository.WorkerRepository;
import springboot.autoservice.service.WorkerService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;

    public WorkerServiceImpl(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    public Worker getById(Long id) {
        return workerRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't get car by id " + id));
    }

    @Override
    public Worker create(Worker worker) {
        return workerRepository.save(worker);
    }

    @Override
    public List<Worker> getAll() {
        return workerRepository.findAll();
    }

    @Override
    public List<Order> getAllCompletedOrdersById(Long id) {
        return workerRepository.getAllCompletedOrdersByWorkerId(id);
    }

    @Override
    public List<Worker> getAllWorkersByOrderId(Long id) {
        return workerRepository.getAllByOrderId(id);
    }
}
