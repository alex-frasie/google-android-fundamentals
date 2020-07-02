package ro.utcn.sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcn.sd.model.Producer;

import java.util.Optional;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, String> {

    Optional<Producer> findByName(String name);
}
