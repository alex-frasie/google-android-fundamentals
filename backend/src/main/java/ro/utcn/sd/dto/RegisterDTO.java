package ro.utcn.sd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

    private String street;
    private Integer number;
    private String county;
    private String city;
    private String postalCode;

}
