package springboot.autoservice.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import springboot.autoservice.model.Favor;
import springboot.autoservice.model.Worker;
import springboot.autoservice.service.FavorService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SalaryServiceImplTest {
    @MockBean
    private FavorService favorService;
    @Autowired
    private SalaryServiceImpl salaryService;

    @Test
    void shouldReturnCorrectSalary() {
        Favor favor = new Favor();
        favor.setId(1L);
        favor.setPrice(BigDecimal.valueOf(100));
        favor.setFavorName("problem with engine");
        favor.setStatus(Favor.Status.PAID);

        Worker worker = new Worker();
        worker.setId(1L);
        worker.setFirstName("BOB");
        worker.setLastName("Chorniy");
        favor.setWorker(worker);

        List<Favor> list = new ArrayList<>();
        list.add(favor);
        Mockito.when(favorService.getByWorkerIdAndOrderId(1L, 1L)).thenReturn(list);

        BigDecimal actual = salaryService.getSalaryByWorkerIdAndOrderId(1L, 1L);
        BigDecimal expected = BigDecimal.valueOf(40.0);
        Assertions.assertEquals(expected, actual);
    }
}
