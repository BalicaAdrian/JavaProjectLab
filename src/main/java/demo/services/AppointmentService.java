package demo.services;

import demo.Exceptions.Exceptions;
import demo.models.Account;
import demo.models.Appointment;
import demo.models.Doctor;
import demo.models.Patient;
import demo.repository.AccountRepository;
import demo.repository.AppointmentRepository;
import demo.repository.DoctorRepository;
import demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Transactional
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> findAll(){
        return appointmentRepository.findAll();
    }

    public List<Appointment> findAllAppointmentForPatient(long patient_id){
        Optional<Patient> patient = patientRepository.findById(patient_id);

        if(patient.isEmpty()) {
            throw Exceptions.patientNotFound();
        }

        return appointmentRepository.getAppointments(patient_id);
    }


    public Appointment findById(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(appointment.isPresent()) {
            return appointment.get();
        } else {
            throw Exceptions.appointmentNotFound();
        }
    }
    @Transactional
    public Optional<Appointment> updateAccountById(long id, Appointment entity) {

        Optional<Appointment> existingAppointment = appointmentRepository.findById(id);
        if(existingAppointment.isPresent()) {
            existingAppointment.get().update(entity);
            appointmentRepository.save(existingAppointment.get());
            return existingAppointment;
        } else {
            throw Exceptions.appointmentNotFound();
        }
    }

    @Transactional
    public Optional<Appointment> closeAppointment(long id) {

        Optional<Appointment> existingAppointment = appointmentRepository.findById(id);
        if(existingAppointment.isPresent()) {
            existingAppointment.get().setEndTime(new Date());
            appointmentRepository.save(existingAppointment.get());
            return existingAppointment;
        } else {
            throw Exceptions.appointmentNotFound();
        }
    }

    @Transactional
    public Optional<Appointment> assignDoctor(long id, long doctor_id) {

        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(appointment.isEmpty()) {
            throw Exceptions.appointmentNotFound();
        }

        Optional<Doctor> doctor = doctorRepository.findById(doctor_id);
        if(doctor.isEmpty()) {
            throw Exceptions.doctorNotFound();
        }

        appointment.get().setDoctor(doctor.get());
        appointmentRepository.save(appointment.get());

        return appointment;
    }
    @Transactional
    public void delete(Appointment appointment){
        appointmentRepository.delete(appointment);
    }

}
