package springboot.autoservice.service;

import java.math.BigDecimal;

public interface SalaryService {
    BigDecimal getSalaryByWorkerIdAndOrderId(Long workerId, Long orderId);
}
