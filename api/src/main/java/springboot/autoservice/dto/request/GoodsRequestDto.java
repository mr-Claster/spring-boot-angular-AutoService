package springboot.autoservice.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class GoodsRequestDto {
    private String name;
    private BigDecimal price;
}
