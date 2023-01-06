package springboot.autoservice.service;

import springboot.autoservice.model.Favor;
import java.util.List;

public interface FavorService {
    Favor getById(Long id);

    Favor create(Favor favor);

    List<Favor> getAll();

    List<Favor> getByWorkerIdAndOrderId(Long workerId, Long orderId);

    Favor changeStatus(Long id, Favor.Status status);
}
