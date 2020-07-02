package ro.utcn.sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.utcn.sd.model.CarPart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarPartRepository extends JpaRepository<CarPart, String> {

    Optional<CarPart> findByName(String name);

    @Query("SELECT cp " +
            "FROM CarPart cp " +
            "WHERE cp.isNew = true")
    List<CarPart> findNewCarParts();

    @Query("SELECT cp " +
            "FROM CarPart cp " +
            "WHERE cp.isNew = false")
    List<CarPart> findOldCarParts();

}
