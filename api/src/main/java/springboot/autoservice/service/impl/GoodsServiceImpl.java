package springboot.autoservice.service.impl;

import springboot.autoservice.model.Goods;
import springboot.autoservice.repository.GoodsRepository;
import springboot.autoservice.service.GoodsService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    private final GoodsRepository goodsRepository;

    public GoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public Goods getById(Long id) {
        return goodsRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't get goods by id " + id));
    }

    @Override
    public Goods create(Goods good) {
        return goodsRepository.save(good);
    }

    @Override
    public List<Goods> getAll() {
        return goodsRepository.findAll();
    }
}
