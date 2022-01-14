package demo.services;

import demo.Exceptions.Exceptions;
import demo.models.*;
import demo.repository.AccountRepository;
import demo.repository.AppointmentRepository;
import demo.repository.DiseaseRepository;
import demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DiseaseService {

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Transactional
    public Disease saveDisease(Disease disease) {
        return diseaseRepository.save(disease);
    }

    public List<Disease> findAll(){
        return diseaseRepository.findAll();
    }

    public Disease findById(Long id) {
        Optional<Disease> disease = diseaseRepository.findById(id);
        if(disease.isPresent()) {
            return disease.get();
        } else {
            throw Exceptions.diseaseNotFound();
        }
    }
    @Transactional
    public Optional<Disease> updateDiseaseById(long id, Disease entity) {

        Optional<Disease> existingDisease = diseaseRepository.findById(id);
        if(existingDisease.isPresent()) {
            existingDisease.get().update(entity);
            diseaseRepository.save(existingDisease.get());
            return existingDisease;
        } else {
            throw Exceptions.diseaseNotFound();
        }
    }

    @Transactional
    public void delete(Disease entity) {
        diseaseRepository.delete(entity);
    }
}
