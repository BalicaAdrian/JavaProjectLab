package demo.controllers;

import demo.Exceptions.Exceptions;
import demo.models.Account;
import demo.models.Appointment;
import demo.models.Patient;
import demo.services.AccountService;
import demo.services.AppointmentService;
import demo.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping(path = "/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Appointment>> getAppointments() {
        List<Appointment> appointments = (List<Appointment>) appointmentService.findAll();

        if (!appointments.isEmpty()) {
            return ResponseEntity.ok(appointments);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/appointments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAppointment(@PathVariable("id") long id) {
        Appointment appointment;
        try{
            appointment = appointmentService.findById(id);

        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        return ResponseEntity.ok(appointment);

    }

    @PostMapping(path = "/appointments")
    public ResponseEntity<Appointment> addAppointment( @RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.saveAppointment(appointment);
        if(createdAppointment == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(createdAppointment);
    }

    @PutMapping(path="/appointments/{id}")
    public ResponseEntity<Object> changeAppointment(@PathVariable("id") long id, @RequestBody Appointment entity) {
        Optional<Appointment> updatedEntity;
        try{
            updatedEntity = appointmentService.updateAccountById(id, entity);
        } catch( Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());

        }
            return ResponseEntity.noContent().build();
    }

    @PutMapping(path="/appointments/{id}/cancel")
    public ResponseEntity<Object> stopAppointment(@PathVariable("id") long id) {
        Optional<Appointment> app;
        try{
            app = appointmentService.closeAppointment(id);
        }catch( Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }
        return ResponseEntity.ok().body(appointmentService.findById(id));

    }

    @PostMapping(path = "/appointment/{id}/doctor/{doctor_id}")
    public ResponseEntity<Object> addAppointment(@PathVariable("id") long id,
                                                  @PathVariable("doctor_id") long doctor_id
    ) {
        Optional<Appointment> appointmentChanged;
        try{
            appointmentChanged = appointmentService.assignDoctor(id, doctor_id);

        } catch(Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }
        return ResponseEntity.ok(appointmentChanged);
    }
}
