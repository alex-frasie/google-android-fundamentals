package ro.sd.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {

    private String role;
    private String username;
    private String firstName;
    private String lastName;

}
