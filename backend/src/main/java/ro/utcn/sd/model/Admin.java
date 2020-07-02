package ro.utcn.sd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admins")
public class Admin {

    @Id
    @Column(name = "id_admin", unique = true, nullable = false)
    private String idAdmin;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "position", length = 50)
    private String position;

    @Column(name = "salary", length = 10)
    private double salary;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

}
