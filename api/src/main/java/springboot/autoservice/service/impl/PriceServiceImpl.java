package springboot.autoservice.service.impl;

import springboot.autoservice.model.Favor;
import springboot.autoservice.model.Goods;
import springboot.autoservice.model.Order;
import springboot.autoservice.service.PriceService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PriceServiceImpl implements PriceService {
    private static final double GOODS_DISCOUNT = 0.01;
    private static final double FAVOR_DISCOUNT = 0.02;

    @Override
    public BigDecimal getTotalPrice(Order order, int bonus) {
        return getFavorsPrice(order, bonus).add(getGoodsPrice(order, bonus));
    }

    private BigDecimal getFavorsPrice(Order order, int bonus) {
        order.getFavors().forEach(f -> f.setPrice(f.getPrice()
                .subtract(f.getPrice()
                        .multiply(BigDecimal.valueOf(bonus * FAVOR_DISCOUNT)))));
        return order.getFavors().stream()
                .map(Favor::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getGoodsPrice(Order order, int bonus) {
        order.getGoods()
                .forEach(g -> g.setPrice(g.getPrice()
                        .subtract(g.getPrice()
                                .multiply(BigDecimal.valueOf(bonus * GOODS_DISCOUNT)))));
        return order.getGoods().stream()
                .map(Goods::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
