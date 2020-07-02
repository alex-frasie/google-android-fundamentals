package ro.utcn.sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.utcn.sd.model.CarPart;
import ro.utcn.sd.model.Cart;
import ro.utcn.sd.model.Customer;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    List<Cart> findByCustomer(Customer customer);

    Optional<Cart> findByCustomerAndCarPart(Customer customer, CarPart carPart);

}
