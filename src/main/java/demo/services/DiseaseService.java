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
public class DiseaseService {

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private MedRepository medRepository;

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
    public Optional<Disease> removeMed(long id, long med_id) {

        Optional<Disease> existingDisease = diseaseRepository.findById(id);
        if(existingDisease.isEmpty()) {
            throw Exceptions.diseaseNotFound();
        }

        Optional<Med> existingMed = medRepository.findById(id);
        if(existingMed.isEmpty()) {
            throw Exceptions.medNotFound();
        }

        boolean ok = false;
        for(Med med: existingDisease.get().getMeds()){
            if(med.getId() == med_id){
                ok = true;
            }
        }

        if(ok == false){
            throw Exceptions.medNotInTreatment();
        }

        existingDisease.get().removeMed(existingMed.get());
        existingDisease.get().setPrice(existingDisease.get().getPrice() - existingMed.get().getMedPrice() * existingMed.get().getQuantity());
        diseaseRepository.save(existingDisease.get());
        return existingDisease;
    }


    @Transactional
    public void delete(Disease entity) {
        diseaseRepository.delete(entity);
    }
}
