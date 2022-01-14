package demo.services;

import demo.Exceptions.Exceptions;
import demo.models.Account;
import demo.models.Appointment;
import demo.models.Disease;
import demo.models.Patient;
import demo.repository.AccountRepository;
import demo.repository.AppointmentRepository;
import demo.repository.DiseaseRepository;
import demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private Patient getPatient(String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isEmpty()) {
            throw Exceptions.patientNotFound();
        }
        return patient.get();
    }
    @Transactional
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> findAll(){
        return patientRepository.findAll();
    }

    public Patient findById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isPresent()) {
            return patient.get();
        } else {
            throw Exceptions.patientNotFound();
        }
    }

    public Optional<Patient> updatePatientById(long id, Patient entity) {

        Optional<Patient> existingPatient = patientRepository.findById(id);
        if(existingPatient.isPresent()) {
            existingPatient.get().update(entity);
            patientRepository.save(existingPatient.get());
            return existingPatient;
        } else {
            throw Exceptions.patientNotFound();
        }
    }

    public Patient createDisease(long id, Disease disease){
        Optional<Patient> patient = patientRepository.findById(id);

        disease.setStartDate(new Date());

        if(patient.isPresent()) {
            disease.setPatient(patient.get());
            diseaseRepository.save(disease);
            patient.get().addDisease(disease);
            return patient.get();
        } else {
            throw Exceptions.patientNotFound();
        }
    }

    public Patient createAccount(long id, Account account){
        Optional<Patient> patient = patientRepository.findById(id);


        if(patient.isPresent()) {
            Patient patientEntity = patient.get();
            account.setName(patientEntity.getFirstName() + " " + patientEntity.getLastName());
            patientEntity.setAccount(account);
            return patientRepository.save(patient.get());
        } else {
            throw Exceptions.patientNotFound();
        }
    }

    public Patient createAppointment(long id, Appointment appointment){
        Optional<Patient> patient = patientRepository.findById(id);

        if(patient.isPresent()) {
            appointment.setPatient(patient.get());
            appointment.setCanceled(false);
            appointmentRepository.save(appointment);
            return patient.get();
        } else {
            throw Exceptions.patientNotFound();
        }
    }

    public void delete(Patient patient){
        patientRepository.delete(patient);
    }
}
