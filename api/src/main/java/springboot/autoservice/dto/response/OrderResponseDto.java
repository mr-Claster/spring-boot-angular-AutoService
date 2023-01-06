package springboot.autoservice.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;
    private Long carId;
    private String problemDescription;
    private LocalDateTime acceptanceDate;
    private List<FavorResponseDto> favors;
    private List<GoodsResponseDto> goods;
    private String status;
    private BigDecimal finalPrice;
    private LocalDateTime endDate;
}
