package springboot.autoservice.dto.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import springboot.autoservice.dto.request.OwnerRequestDto;
import springboot.autoservice.dto.response.OrderResponseDto;
import springboot.autoservice.dto.response.OwnerResponseDto;
import springboot.autoservice.model.Order;
import springboot.autoservice.model.Owner;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class OwnerMapperTest {
    @Autowired
    private OwnerMapper ownerMapper;
    @MockBean
    private OrderMapper orderMapper;

    @Test
    void shouldReturnOwnerResponseDto() {
        Order order = new Order();
        order.setId(1L);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);

        Owner owner = new Owner();
        owner.setId(2L);
        owner.setFirstName("first name");
        owner.setLastName("last name");
        owner.setOrders(orderList);

        OrderResponseDto orderMockito = new OrderResponseDto();
        orderMockito.setId(1L);
        Mockito.when(orderMapper.mapToDto(order)).thenReturn(orderMockito);

        OwnerResponseDto actual = ownerMapper.mapToDto(owner);

        OrderResponseDto orderExpected = new OrderResponseDto();
        orderExpected.setId(1L);

        OwnerResponseDto expected = new OwnerResponseDto();
        expected.setId(2L);
        expected.setFirstName("first name");
        expected.setLastName("last name");
        expected.setOrders(List.of(orderExpected));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldReturnOwner() {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setFirstName("first name");
        ownerRequestDto.setLastName("last name");

        Owner actual = ownerMapper.mapToModel(ownerRequestDto);

        Owner expected = new Owner();
        expected.setFirstName("first name");
        expected.setLastName("last name");

        Assertions.assertEquals(expected, actual);
    }
}
