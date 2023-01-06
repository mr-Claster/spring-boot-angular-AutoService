package springboot.autoservice.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class WorkerResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<OrderResponseDto> completedOrders;
}
