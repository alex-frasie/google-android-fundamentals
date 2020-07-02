package ro.sd.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerDTO {

    private String name;
    private String country;
    private Integer startingYear;

}
