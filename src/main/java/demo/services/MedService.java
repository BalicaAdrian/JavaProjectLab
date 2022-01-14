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
public class MedService {

    @Autowired
    private MedRepository medRepository;

    @Transactional
    public Med saveMed(Med med) {
        Optional<Med> medication = medRepository.getMed(med.getName(), med.getQuantity());
        if(medication.isPresent()){
            throw Exceptions.medAlreadyExist();
        }

        if(med.getQuantity() == 0 || med.getMedPrice() == 0){
            throw Exceptions.medPriceZero();
        }
        return medRepository.save(med);
    }

    public List<Med> findAll(){
        return medRepository.findAll();
    }

    public Med findById(Long id) {
        Optional<Med> med = medRepository.findById(id);
        if(med.isPresent()) {
            return med.get();
        } else {
            throw Exceptions.medNotFound();
        }
    }

    public Med findByName(String name) {
        Optional<Med> med = medRepository.findByName(name);
        if(med.isPresent()) {
            return med.get();
        } else {
            throw Exceptions.medNotFound();
        }
    }

    public Med findByNameAndQty(String name, int qty) {
        Optional<Med> med = medRepository.getMed(name, qty);
        if(med.isPresent()) {
            return med.get();
        } else {
            throw Exceptions.medNotFound();
        }
    }

    @Transactional
    public Optional<Med> updateMedById(long id, Med entity) {

        Optional<Med> existingMed = medRepository.findById(id);
        if(existingMed.isPresent()) {
            existingMed.get().update(entity);
            medRepository.save(existingMed.get());
            return existingMed;
        } else {
            throw Exceptions.medNotFound();
        }
    }

    @Transactional
    public void delete(Med entity) {
        medRepository.delete(entity);
    }
}
