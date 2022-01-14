package demo.repository;

import demo.models.Appointment;
import demo.models.Med;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value="select * from appointment JOIN patient  where appointment.patient = patient.id  AND patient.id = :id ", nativeQuery = true)
    List<Appointment> getAppointments(long id);

}
