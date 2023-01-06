package springboot.autoservice.service;

import springboot.autoservice.model.Order;
import java.math.BigDecimal;

public interface PriceService {
    BigDecimal getTotalPrice(Order order, int bonus);
}
