package springboot.autoservice.service;

import springboot.autoservice.model.Order;
import springboot.autoservice.model.Worker;
import java.util.List;

public interface WorkerService {

    Worker getById(Long id);

    Worker create(Worker worker);

    List<Worker> getAll();

    List<Order> getAllCompletedOrdersById(Long id);

    List<Worker> getAllWorkersByOrderId(Long id);
}
