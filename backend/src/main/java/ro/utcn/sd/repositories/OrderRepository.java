package ro.utcn.sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcn.sd.model.Customer;
import ro.utcn.sd.model.Order;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByCustomer(Customer customer);

    List<Order> findByCustomerAndPlacementDateAfterAndPlacementDateBefore(Customer customer, LocalDateTime from, LocalDateTime to);
}
