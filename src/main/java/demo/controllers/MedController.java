package demo.controllers;

import demo.Exceptions.Exceptions;
import demo.models.*;
import demo.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MedController {

    private MedService medService;
    private DiseaseService diseaseService;


    public MedController(MedService medService, DiseaseService diseaseService) {
        this.medService = medService;
        this.diseaseService = diseaseService;
    }

    @GetMapping(path = "/meds", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Med>> getMeds() {
        List<Med> meds = (List<Med>) medService.findAll();
        if (!meds.isEmpty()) {
            return ResponseEntity.ok(meds);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/meds/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMed(@PathVariable("id") long id) {
        Med med;
        try{
             med = medService.findById(id);
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        return ResponseEntity.ok(med);

    }

    @PostMapping(path = "/meds")
    public ResponseEntity<Object> addMed( @RequestBody Med med) {
        Med createdMed;
        try{
            createdMed = medService.saveMed(med);
            if(createdMed == null){
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getError());
        }

        return ResponseEntity.ok(createdMed);
    }

    @PutMapping(path="/meds/{id}")
    public ResponseEntity<Object> changeMed(@PathVariable("id") long id, @RequestBody Med entity) {
        Optional<Med> updatedEntity;
        try{
            updatedEntity = medService.updateMedById(id, entity);
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/meds/{id}")
    public ResponseEntity<Object> removePerson(@PathVariable("id") long id) {
        Med existingMed;

        try{
            existingMed = medService.findById(id);
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

            for (Disease disease : existingMed.getDiseases()) {
                disease.removeMed(existingMed);
                disease.setPrice(disease.getPrice() - existingMed.getMedPrice() * existingMed.getQuantity());
                diseaseService.saveDisease(disease);
            }
            medService.delete(existingMed);
            return ResponseEntity.noContent().build();

    }


}
