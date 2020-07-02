package ro.utcn.sd.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @Column(name = "id_address", unique = true, nullable = false)
    private String idAddress;

    @Column(name = "street", length = 100)
    private String street;

    @Column(name = "number")
    private Integer number;

    @Column(name = "county", length = 40)
    private String county;

    @Column(name = "city", length = 40)
    private String city;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    public String prettyAddress(){
        return this.getNumber() + ", " +
                this.getStreet() + " street, " +
                this.getCity() + ", " +
                this.getCounty() + ", " +
                "postal code: " + this.getPostalCode();
    }

}
