package ro.sd.client.dto;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @SerializedName("credential")
    private String credential;

    @SerializedName("password")
    private String password;

}
