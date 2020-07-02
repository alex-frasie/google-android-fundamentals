package ro.utcn.sd.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id_order", unique = true, nullable = false)
    private String idOrder;

    @Column(name = "placement_date", length = 20)
    private LocalDateTime placementDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_customer", referencedColumnName = "id_customer")
    private Customer customer;

}
