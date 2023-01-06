package springboot.autoservice.dto.response;

import lombok.Data;

@Data
public class CarResponseDto {
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private String serialNumber;
    private Long ownerId;
}
