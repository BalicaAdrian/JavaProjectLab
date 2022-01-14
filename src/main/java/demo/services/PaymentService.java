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
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Transactional
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> findAll(){
        return paymentRepository.findAll();
    }

    public Payment findById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isPresent()) {
            return payment.get();
        } else {
            throw Exceptions.paymentNotFound();
        }
    }
    @Transactional
    public Optional<Payment> updatePaymentById(long id, Payment entity) {

        Optional<Payment> existingPayment = paymentRepository.findById(id);
        if(existingPayment.isPresent()) {
            existingPayment.get().update(entity);
            paymentRepository.save(existingPayment.get());
            return existingPayment;
        } else {
            throw Exceptions.paymentNotFound();
        }
    }

    @Transactional
    public Payment createPayment(long patient_id, long doctor_id, Payment payment) {

        Optional<Patient> existingPatient = patientRepository.findById(patient_id);
        Optional<Doctor> existingDoctor = doctorRepository.findById(doctor_id);

        if(existingPatient.isEmpty()) {
            throw Exceptions.patientNotFound();
        }

        if(existingDoctor.isEmpty()) {
            throw Exceptions.doctorNotFound();
        }

        payment.setSender(existingPatient.get().getAccount());
        payment.setReceiver(existingDoctor.get().getAccount());

        if(payment.getAmount() > existingPatient.get().getAccount().getAmount()){
            throw Exceptions.notEnoughFounds();
        }
        existingDoctor.get().getAccount().setAmount(existingDoctor.get().getAccount().getAmount() + payment.getAmount());
        existingPatient.get().getAccount().setAmount(existingPatient.get().getAccount().getAmount() - payment.getAmount());

        payment.setAlreadyUndo(false);
        paymentRepository.save(payment);
        doctorRepository.save(existingDoctor.get());
        patientRepository.save(existingPatient.get());

        return payment;
    }

    @Transactional
    public void undoPayment(long id) {

        Optional<Payment> existingPayment = paymentRepository.findById(id);

        if (existingPayment.isEmpty()){
            throw Exceptions.paymentNotFound();
        }

        if(existingPayment.get().isAlreadyUndo()){
            throw Exceptions.alreadyUndo();
        }

        existingPayment.get().getReceiver().setAmount( existingPayment.get().getReceiver().getAmount() - existingPayment.get().getAmount());
        existingPayment.get().getSender().setAmount(existingPayment.get().getSender().getAmount() + existingPayment.get().getAmount());
        existingPayment.get().setAlreadyUndo(true);
        paymentRepository.save(existingPayment.get());
        doctorRepository.save(existingPayment.get().getReceiver().getDoctor());
        patientRepository.save(existingPayment.get().getSender().getPatient());
    }
}
