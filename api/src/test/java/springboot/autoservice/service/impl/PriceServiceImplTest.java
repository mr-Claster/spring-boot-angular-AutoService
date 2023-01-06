package springboot.autoservice.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springboot.autoservice.model.Favor;
import springboot.autoservice.model.Goods;
import springboot.autoservice.model.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PriceServiceImplTest {
    @Autowired
    private PriceServiceImpl priceService;

    @Test
    void shouldReturnCorrectPrice() {
        List<Goods> goods = new ArrayList<>();
        Goods good1 = new Goods();
        good1.setPrice(BigDecimal.valueOf(200));
        Goods good2 = new Goods();
        good2.setPrice(BigDecimal.valueOf(300));
        goods.add(good1);
        goods.add(good2);

        List<Favor> favors = new ArrayList<>();
        Favor favor1 = new Favor();
        favor1.setPrice(BigDecimal.valueOf(500));
        Favor favor2 = new Favor();
        favor2.setPrice(BigDecimal.valueOf(200));
        favors.add(favor1);
        favors.add(favor2);

        Order order = new Order();
        order.setGoods(goods);
        order.setFavors(favors);

        BigDecimal actual = priceService.getTotalPrice(order, 20);
        BigDecimal expected = BigDecimal.valueOf(820.0);

        Assertions.assertEquals(expected, actual);
    }
}
