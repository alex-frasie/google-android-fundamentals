package ro.sd.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarPartDTO {

    private String name;
    private boolean isNew;
    private Integer year;
    private String description;
    private double price;
    private Integer quantityAvailable;
    private String producerName;

}
