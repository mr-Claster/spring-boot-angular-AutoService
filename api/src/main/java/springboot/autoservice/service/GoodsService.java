package springboot.autoservice.service;

import springboot.autoservice.model.Goods;
import java.util.List;

public interface GoodsService {
    Goods getById(Long id);

    Goods create(Goods good);

    List<Goods> getAll();
}
