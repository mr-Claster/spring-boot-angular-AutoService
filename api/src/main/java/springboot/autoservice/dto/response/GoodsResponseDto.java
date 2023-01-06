package springboot.autoservice.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class GoodsResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
