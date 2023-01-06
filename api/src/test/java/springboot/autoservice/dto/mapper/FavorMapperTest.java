package springboot.autoservice.dto.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springboot.autoservice.dto.response.FavorResponseDto;
import springboot.autoservice.model.Favor;
import springboot.autoservice.model.Worker;
import java.math.BigDecimal;
import java.util.ArrayList;

@SpringBootTest
class FavorMapperTest {
    @Autowired
    private FavorMapper favorMapper;

    @Test
    void shouldReturnFavorResponseDto() {
        Worker worker = new Worker();
        worker.setId(1L);
        worker.setFirstName("first name");
        worker.setLastName("last name");
        worker.setCompletedOrders(new ArrayList<>());

        Favor favor = new Favor();
        favor.setId(1L);
        favor.setFavorName("favor name");
        favor.setStatus(Favor.Status.PAID);
        favor.setPrice(BigDecimal.valueOf(100));
        favor.setWorker(worker);

        FavorResponseDto actual = favorMapper.mapToDto(favor);

        FavorResponseDto expected = new FavorResponseDto();
        expected.setId(1L);
        expected.setWorkerId(1L);
        expected.setFavorName("favor name");
        expected.setStatus("PAID");
        expected.setPrice(BigDecimal.valueOf(100));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFavor() {
        Worker worker = new Worker();
        worker.setId(1L);

        Favor favor = new Favor();
        favor.setId(1L);
        favor.setFavorName("favor name");
        favor.setWorker(worker);
        favor.setPrice(BigDecimal.valueOf(100));
        favor.setStatus(Favor.Status.PAID);

        FavorResponseDto actual = favorMapper.mapToDto(favor);

        FavorResponseDto expected = new FavorResponseDto();
        expected.setId(1L);
        expected.setFavorName("favor name");
        expected.setWorkerId(1L);
        expected.setPrice(BigDecimal.valueOf(100));
        expected.setStatus("PAID");

        Assertions.assertEquals(expected, actual);
    }
}
