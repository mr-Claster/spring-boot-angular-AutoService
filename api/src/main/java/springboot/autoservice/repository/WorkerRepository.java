package springboot.autoservice.repository;

import springboot.autoservice.model.Order;
import springboot.autoservice.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Query("select distinct o from Order o "
            + "inner join fetch o.favors f "
            + "inner join fetch f.worker r "
            + "where r.id = :id AND o.status = 'COMPLETED' ")
    List<Order> getAllCompletedOrdersByWorkerId(Long id);

    @Query("select rep from Worker rep where rep.id in "
            + "(select r.id from Order o "
            + "inner join o.favors f "
            + "inner join f.worker r "
            + "where o.id = :id)")
    List<Worker> getAllByOrderId(Long id);
}
