package ro.utcn.sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcn.sd.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
}
