package ro.utcn.sd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateFilterDTO {

    private String username;

    private Integer dayFrom;
    private Integer monthFrom;
    private Integer yearFrom;

    private Integer dayTo;
    private Integer monthTo;
    private Integer yearTo;
}
