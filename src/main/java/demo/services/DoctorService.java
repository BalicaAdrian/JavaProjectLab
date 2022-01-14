package demo.services;

import demo.Exceptions.Exceptions;
import demo.models.*;
import demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> findAll(){
        return doctorRepository.findAll();
    }

    public Doctor findById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isPresent()) {
            return doctor.get();
        } else {
            throw Exceptions.doctorNotFound();
        }
    }
    @Transactional
    public Optional<Doctor> updateDiseaseById(long id, Doctor entity) {

        Optional<Doctor> existingDoctor = doctorRepository.findById(id);
        if(existingDoctor.isPresent()) {
            existingDoctor.get().update(entity);
            doctorRepository.save(existingDoctor.get());
            return existingDoctor;
        } else {
            throw Exceptions.doctorNotFound();
        }
    }
    @Transactional
    public Doctor createAccount(long id, Account account){
        Optional<Doctor> doctor = doctorRepository.findById(id);

        if(doctor.isPresent()) {
            Doctor doctorEntity = doctor.get();
            account.setName(doctorEntity.getFirstName() + " " + doctorEntity.getLastName());
            doctorEntity.setAccount(account);
            return doctorRepository.save(doctor.get());
        } else {
            throw Exceptions.doctorNotFound();
        }
    }

    @Transactional
    public void delete(Doctor doc){
       doctorRepository.delete(doc);
    }
}
