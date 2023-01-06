package springboot.autoservice.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FavorRequestDto {
    private String favorName;
    private Long workerId;
    private BigDecimal price;
}
