package demo.controllers;

import demo.Exceptions.Exceptions;
import demo.models.Patient;
import demo.models.Payment;
import demo.services.PatientService;
import demo.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(path = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Payment>> getPayments() {
        List<Payment> payments = (List<Payment>) paymentService.findAll();
        if (!payments.isEmpty()) {
            return ResponseEntity.ok(payments);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/payments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPayment(@PathVariable("id") long id) {
        Payment payment;
        try{
             payment = paymentService.findById(id);
        }catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        return ResponseEntity.ok(payment);

    }

    @PostMapping(path = "/payments")
    public ResponseEntity<Payment> addPayment( @RequestBody Payment payment) {
        Payment createdPayment = paymentService.savePayment(payment);
        if(createdPayment == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(createdPayment);
    }

    @PostMapping(path = "/payments/patient/{patient_id}/doctors/{doctor_id}")
    public ResponseEntity<Object> createPayment(@PathVariable("patient_id") long patient_id,
                                                 @PathVariable("doctor_id") long doctor_id,
                                                 @RequestBody Payment payment) {
        Payment createdPayment;
        try{
        createdPayment = paymentService.createPayment(patient_id, doctor_id, payment);
        } catch ( Exceptions e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getError());
        }
        return ResponseEntity.ok(createdPayment);
    }

    @PutMapping(path="/payments/{id}")
    public ResponseEntity<Object> changePayment(@PathVariable("id") long id, @RequestBody Payment entity) {
        Optional<Payment> updatedEntity;
        try {
            updatedEntity = paymentService.updatePaymentById(id, entity);
        } catch (Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path="/undo-payments/{id}")
    public ResponseEntity<Object> undoPayment(@PathVariable("id") long id) {
        try{
            paymentService.undoPayment(id);
        } catch ( Exceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
        }
        return ResponseEntity.noContent().build();
    }

}
