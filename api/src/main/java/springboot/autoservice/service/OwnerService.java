package springboot.autoservice.service;

import springboot.autoservice.model.Car;
import springboot.autoservice.model.Order;
import springboot.autoservice.model.Owner;
import java.util.List;

public interface OwnerService {
    Owner getById(Long id);

    Owner create(Owner owner);

    List<Owner> getAll();
}
