package springboot.autoservice.service.impl;

import springboot.autoservice.model.Favor;
import springboot.autoservice.service.FavorService;
import springboot.autoservice.service.SalaryService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {
    private static final BigDecimal REPAIRMAN_DISCOUNT = BigDecimal.valueOf(0.4);
    private final FavorService favorService;

    public SalaryServiceImpl(FavorService favorService) {
        this.favorService = favorService;
    }

    @Override
    public BigDecimal getSalaryByWorkerIdAndOrderId(Long workerId, Long orderId) {
        List<Favor> favors = favorService.getByWorkerIdAndOrderId(workerId, orderId);
        favors.forEach(f -> favorService.changeStatus(f.getId(), Favor.Status.PAID));
        return favors.stream()
                .map(f -> f.getPrice()
                        .multiply(REPAIRMAN_DISCOUNT))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
