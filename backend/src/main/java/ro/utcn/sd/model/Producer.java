package ro.utcn.sd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "producers")
public class Producer {

    @Id
    @Column(name = "id_producer", unique = true, nullable = false)
    private String idProducer;

    @Column(name = "company_name", length = 100)
    private String name;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "statring_year", length = 100)
    private Integer startingYear;

}
