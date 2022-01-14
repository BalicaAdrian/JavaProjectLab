package demo.controllers;

import demo.Exceptions.Exceptions;
import demo.models.*;
import demo.services.*;
import org.hibernate.jdbc.Expectations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DoctorController {

    private DoctorService doctorService;
    private AppointmentService appointmentService;
    private AccountService accountService;

    public DoctorController(DoctorService doctorService, AppointmentService appointmentService, AccountService accountService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.accountService = accountService;
    }

    @GetMapping(path = "/doctors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Doctor>> getDoctors() {
        List<Doctor> doctors = (List<Doctor>) doctorService.findAll();
        if (!doctors.isEmpty()) {
            return ResponseEntity.ok(doctors);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/doctors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDoctor(@PathVariable("id") long id) {
        Doctor doctor;
        try{
            doctor = doctorService.findById(id);
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        return ResponseEntity.ok(doctor);

    }

    @PostMapping(path = "/doctors")
    public ResponseEntity<Doctor> addDoctor( @RequestBody Doctor doctor) {
        Doctor createdDoctor = doctorService.saveDoctor(doctor);
        if(createdDoctor == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(createdDoctor);
    }

    @PostMapping(path = "/doctors/{id}/create-account")
    public ResponseEntity<Object> createAccount( @PathVariable("id") long id, @RequestBody Account account) {
        Doctor updatedDoctor;
        try{
            updatedDoctor = doctorService.createAccount(id, account);
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }
        return ResponseEntity.ok(updatedDoctor);
    }

    @PutMapping(path="/doctors/{id}")
    public ResponseEntity<Object> changeDisease(@PathVariable("id") long id, @RequestBody Doctor entity) {
        Optional<Doctor> updatedEntity;
        try {
            updatedEntity = doctorService.updateDiseaseById(id, entity);
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        return ResponseEntity.ok().body(doctorService.findById(id));

    }

    @DeleteMapping(path = "/doctors/{id}")
    public ResponseEntity<Object> removeDoctor(@PathVariable("id") long id) {
        try {
            Doctor doc = doctorService.findById(id);

            for (Appointment app : doc.getAppointments()) {
                app.removeDoctor();
                appointmentService.saveAppointment(app);
            }
            doc.deleteAccount();
            doctorService.delete(doc);
        } catch(Exceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }
        return ResponseEntity.noContent().build();

    }

}
