package springboot.autoservice.service.impl;

import springboot.autoservice.model.Favor;
import springboot.autoservice.repository.FavorRepository;
import springboot.autoservice.service.FavorService;
import org.springframework.stereotype.Service;
import springboot.autoservice.service.OrderService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavorServiceImpl implements FavorService {
    private final FavorRepository favorRepository;
    private final OrderService orderService;

    public FavorServiceImpl(FavorRepository favorRepository,
                            OrderService orderService) {
        this.favorRepository = favorRepository;
        this.orderService = orderService;
    }

    @Override
    public Favor getById(Long id) {
        return favorRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't get favor by id " + id));
    }

    @Override
    public Favor create(Favor favor) {
        return favorRepository.save(favor);
    }

    @Override
    public List<Favor> getAll() {
        return favorRepository.findAll();
    }

    @Override
    public List<Favor> getByWorkerIdAndOrderId(Long workerId, Long orderId) {
        return orderService.getById(orderId).getFavors()
                .stream()
                .filter(f -> f.getWorker().getId().equals(workerId))
                .collect(Collectors.toList());
    }

    @Override
    public Favor changeStatus(Long id, Favor.Status status) {
        favorRepository.changeFavorStatusById(id, status);
        return getById(id);
    }
}
