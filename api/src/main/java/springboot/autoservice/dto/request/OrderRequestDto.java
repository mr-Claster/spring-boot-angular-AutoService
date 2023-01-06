package springboot.autoservice.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderRequestDto {
    private Long carId;
    private String problemDescription;
    private LocalDateTime acceptanceDate;
}
