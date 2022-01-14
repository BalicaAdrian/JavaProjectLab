package demo.controllers;

import demo.Exceptions.Exceptions;
import demo.models.*;
import demo.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DiseaseController {

    private DiseaseService diseaseService;
    private MedService medService;

    public DiseaseController(DiseaseService diseaseService, MedService medService) {
        this.diseaseService = diseaseService;
        this.medService = medService;
    }

    @GetMapping(path = "/diseases", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Disease>> getDiseases() {
        List<Disease> diseases = (List<Disease>) diseaseService.findAll();
        if (!diseases.isEmpty()) {
            return ResponseEntity.ok(diseases);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/diseases/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDisease(@PathVariable("id") long id) {
        Disease disease;
        try{
            disease = diseaseService.findById(id);
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }
        return ResponseEntity.ok(disease);

    }

    @PostMapping(path = "/diseases")
    public ResponseEntity<Disease> addDisease( @RequestBody Disease disease) {
        disease.setPrice(0);
        Disease createdDisease = diseaseService.saveDisease(disease);
        if(createdDisease == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(createdDisease);
    }

    @PostMapping(path = "/diseases/{id}/meds")
    public ResponseEntity<Object> addMeds(@PathVariable("id") long id, @RequestParam("medName") String medName, @RequestParam("qty") int qty) {
        Med med;
        Disease disease;
        try {
            med = medService.findByNameAndQty(medName, qty);
            disease = diseaseService.findById(id);
        } catch (Exceptions e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        disease.setPrice(disease.getPrice() + med.getQuantity() * med.getMedPrice());
        disease.addMeds(med);
        Disease savedDisease = diseaseService.saveDisease(disease);
        if(savedDisease == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(savedDisease);
    }

    @PutMapping(path="/diseases/{id}")
    public ResponseEntity<Object> changeDisease(@PathVariable("id") long id, @RequestBody Disease entity) {
        Optional<Disease> updatedEntity;
        try{
            updatedEntity = diseaseService.updateDiseaseById(id, entity);
        } catch( Exceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        return ResponseEntity.ok().body(diseaseService.findById(id));

    }

    @PutMapping(path="/diseases/{id}/med/{med_id}")
    public ResponseEntity<Object> changeDisease(@PathVariable("id") long id, @PathVariable("med_id") long med_id) {
        Optional<Disease> updatedEntity;
        try{
            updatedEntity = diseaseService.removeMed(id, med_id);
        } catch( Exceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        return ResponseEntity.ok().body(diseaseService.findById(id));

    }


    @DeleteMapping(path = "/diseases/{id}")
    public ResponseEntity<Object> removeDisease(@PathVariable("id") long id) {
        Disease existingDisease;
        try{
           existingDisease = diseaseService.findById(id);
        }catch( Exceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        for (Med med : existingDisease.getMeds()) {
            med.getDiseases().remove(existingDisease);
            medService.saveMed(med);
        }
        diseaseService.delete(existingDisease);
        return ResponseEntity.noContent().build();

    }

}
