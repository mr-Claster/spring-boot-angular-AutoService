package springboot.autoservice.dto.request;

import lombok.Data;

@Data
public class CarRequestDto {
    private String brand;
    private String model;
    private Integer year;
    private String serialNumber;
    private Long ownerId;
}
