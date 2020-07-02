package ro.sd.client.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @SerializedName("placementDate")
    @Expose
    private String placementDate;

    @SerializedName("totalAmount")
    @Expose
    private Double totalAmount;

    @SerializedName("customerName")
    @Expose
    private String customerName;

    @SerializedName("description")
    @Expose
    private String description;

}
