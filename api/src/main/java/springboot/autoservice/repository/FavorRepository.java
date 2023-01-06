package springboot.autoservice.repository;

import springboot.autoservice.model.Favor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import springboot.autoservice.model.Worker;
import java.util.List;
import javax.transaction.Transactional;

public interface FavorRepository extends JpaRepository<Favor, Long> {
    @Transactional
    @Modifying
    @Query("update Favor f set f.status = :status where f.id = :id")
    void changeFavorStatusById(Long id, Favor.Status status);
}
