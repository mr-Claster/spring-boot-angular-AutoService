package springboot.autoservice.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FavorResponseDto {
    private Long id;
    private String favorName;
    private Long workerId;
    private BigDecimal price;
    private String status;
}
