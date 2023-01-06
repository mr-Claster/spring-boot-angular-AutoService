package springboot.autoservice.dto.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import springboot.autoservice.dto.request.WorkerRequestDto;
import springboot.autoservice.dto.response.OrderResponseDto;
import springboot.autoservice.dto.response.WorkerResponseDto;
import springboot.autoservice.model.Order;
import springboot.autoservice.model.Worker;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class WorkerMapperTest {
    @Autowired
    private WorkerMapper workerMapper;
    @MockBean
    private OrderMapper orderMapper;

    @Test
    void shouldReturnWorkerResponseDto() {
        Order order = new Order();
        order.setId(1L);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);

        Worker worker = new Worker();
        worker.setId(2L);
        worker.setFirstName("first name");
        worker.setLastName("last name");
        worker.setCompletedOrders(orderList);

        OrderResponseDto orderMockito = new OrderResponseDto();
        orderMockito.setId(1L);
        Mockito.when(orderMapper.mapToDto(order)).thenReturn(orderMockito);

        WorkerResponseDto actual = workerMapper.mapToDto(worker);

        OrderResponseDto orderExpected = new OrderResponseDto();
        orderExpected.setId(1L);

        WorkerResponseDto expected = new WorkerResponseDto();
        expected.setId(2L);
        expected.setFirstName("first name");
        expected.setLastName("last name");
        expected.setCompletedOrders(List.of(orderExpected));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldReturnWorker() {
        WorkerRequestDto workerRequestDto = new WorkerRequestDto();
        workerRequestDto.setFirstName("first name");
        workerRequestDto.setLastName("last name");

        Worker actual = workerMapper.mapToModel(workerRequestDto);

        Worker expected = new Worker();
        expected.setFirstName("first name");
        expected.setLastName("last name");

        Assertions.assertEquals(expected, actual);
    }
}
