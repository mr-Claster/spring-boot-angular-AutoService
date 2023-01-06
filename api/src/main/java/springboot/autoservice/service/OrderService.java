package springboot.autoservice.service;

import springboot.autoservice.model.Order;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order getById(Long id);

    Order create(Order order);

    List<Order> getAll();

    BigDecimal setFinalPrice(Long id, int bonus);

    Order changeStatus(Long id, Order.Status status);
}
