package demo.controllers;

import demo.Exceptions.Exceptions;
import demo.models.*;
import demo.services.AppointmentService;
import demo.services.DiseaseService;
import demo.services.MedService;
import demo.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PatientController {

    private PatientService patientService;
    private DiseaseService diseaseService;
    private MedService medService;
    private AppointmentService appointmentService;

    public PatientController(PatientService patientService, DiseaseService diseaseService, MedService medService, AppointmentService appointmentService) {
        this.patientService = patientService;
        this.diseaseService = diseaseService;
        this.medService = medService;
        this.appointmentService = appointmentService;
    }

    @GetMapping(path = "/patients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Patient>> getPatients() {
        List<Patient> patients = (List<Patient>) patientService.findAll();
        if (!patients.isEmpty()) {
            return ResponseEntity.ok(patients);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/patients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPerson(@PathVariable("id") long id) {
        Patient patient;
        try {
            patient = patientService.findById(id);
        } catch ( Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }
        return ResponseEntity.ok(patient);

    }

    @PostMapping(path = "/patients")
    public ResponseEntity<Patient> addPatient( @RequestBody Patient patient) {
       Patient createdPatient = patientService.savePatient(patient);
       if(createdPatient == null){
           return ResponseEntity.internalServerError().build();
       }
        return ResponseEntity.ok(createdPatient);
    }

    @PostMapping(path = "/patients/{id}/disease")
    public ResponseEntity<Object> addDisease( @PathVariable("id") long id, @RequestBody Disease disease) {
        Patient updatedPatient;
        try {
            updatedPatient = patientService.createDisease(id, disease);
        } catch (Exceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }
        return ResponseEntity.ok(updatedPatient);
    }

    @PostMapping(path = "/patients/{id}/create-account")
    public ResponseEntity<Object> createAccount( @PathVariable("id") long id, @RequestBody Account account) {
        Patient updatedPatient;
        try {
            updatedPatient = patientService.createAccount(id, account);
        } catch (Exceptions e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
            }
        return ResponseEntity.ok(updatedPatient);
    }

    @PostMapping(path = "/patients/{id}/appointment")
    public ResponseEntity<Object> addAppointment( @PathVariable("id") long id, @RequestBody Appointment appointment) {
        Patient updatedPatient;
        try {
            updatedPatient = patientService.createAppointment(id, appointment);
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }
        return ResponseEntity.ok(patientService.findById(id));
    }

    @PutMapping(path="/patients/{id}")
    public ResponseEntity<Object> changePatient(@PathVariable("id") long id, @RequestBody Patient entity) {
        try{
           patientService.updatePatientById(id, entity);
        } catch (Exceptions e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }
            return ResponseEntity.notFound().build();

    }

    @DeleteMapping(path = "/patients/{id}")
    public ResponseEntity<Object> removePatient(@PathVariable("id") long id) {
        Patient patient;
        try{
            patient = patientService.findById(id);
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        for (Disease disease : patient.getDiseases()) {
            for (Med med : disease.getMeds()) {
                med.getDiseases().remove(disease);
                medService.saveMed(med);
            }
            patient.removeDisease(disease);
            patientService.savePatient(patient);
            diseaseService.delete(disease);
        }

        for (Appointment app : patient.getAppointments()) {
            app.removeDoctor();
            app.removePatient();
            patient.removeAppointments(app);
            patientService.savePatient(patient);
            appointmentService.delete(app);
        }
        patient.removeAccount();
        patientService.delete(patient);

        return ResponseEntity.noContent().build();
    }

}
