package ro.utcn.sd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String street;
    private Integer number;
    private String county;
    private String city;
    private String postalCode;

}
