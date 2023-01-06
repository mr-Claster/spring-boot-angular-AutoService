package springboot.autoservice.service.impl;

import springboot.autoservice.model.Favor;
import springboot.autoservice.model.Order;
import springboot.autoservice.model.Worker;
import springboot.autoservice.repository.OrderRepository;
import springboot.autoservice.service.OrderService;
import springboot.autoservice.service.PriceService;
import org.springframework.stereotype.Service;
import springboot.autoservice.service.WorkerService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PriceService priceService;
    private final WorkerService workerService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            PriceService priceService,
                            WorkerService workerService) {
        this.orderRepository = orderRepository;
        this.priceService = priceService;
        this.workerService = workerService;
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't get order by id " + id));
    }

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public BigDecimal setFinalPrice(Long id, int bonus) {
        Order order = getById(id);
        BigDecimal sum = priceService.getTotalPrice(order, bonus);
        order.setFinalPrice(sum);
        create(order);
        return sum;
    }

    @Override
    public Order changeStatus(Long id, Order.Status status) {
        Order order = getById(id);
        order.setStatus(status);
        List<Worker> workerList;
        switch (status) {
            case COMPLETED: {
                workerList = workerService.getAllWorkersByOrderId(id);
                saveData(order, workerList);
                break;
            }
            case FAILURE: {
                workerList = changeToFailure(order);
                saveData(order, workerList);
                break;
            }
            default:
        }
        return order;
    }

    private List<Worker> changeToFailure(Order order) {
        Favor priceFavor = order.getFavors().stream()
                .findFirst()
                .get();
        priceFavor.setPrice(BigDecimal.valueOf(500));
        return List.of(priceFavor.getWorker());
    }

    private void saveData(Order order, List<Worker> workerList) {
        order.setEndDate(LocalDateTime.now());
        create(order);
        workerList.forEach(w -> w.getCompletedOrders().add(order));
        workerList.forEach(workerService::create);
    }
}
