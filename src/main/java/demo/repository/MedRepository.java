package demo.repository;

import demo.models.Med;
import demo.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedRepository extends JpaRepository<Med, Long> {
    Optional<Med> findByName(String name);

    @Query(value="select distinct * from med where name = :name AND quantity = :quantity ", nativeQuery = true)
    Optional<Med> getMed(String name, int quantity);
}
