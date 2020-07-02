package ro.utcn.sd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carparts")
public class CarPart {

    @Id
    @Column(name = "id_car_part", unique = true, nullable = false)
    private String idCarPart;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "is_new")
    private boolean isNew;

    @Column(name = "year")
    private Integer year;

    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity_available")
    private Integer quantityAvailable;

    @OneToOne
    @JoinColumn(name = "id_producer")
    private Producer producer;

    public CarPart(String name, boolean isNew, Integer year, String description, double price, Integer quantityAvailable) {
        this.name = name;
        this.isNew = isNew;
        this.year = year;
        this.description = description;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }
}
