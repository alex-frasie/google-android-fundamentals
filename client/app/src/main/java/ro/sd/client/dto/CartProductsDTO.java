package ro.sd.client.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProductsDTO {

    @SerializedName("carPartCartDTOs")
    @Expose
    private List<CarPartCartDTO> carPartCartDTOs;
}
