package demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="amount", nullable = false)
    private long amount;

    @OneToOne(mappedBy = "account")
    @JsonBackReference(value="patient")
    private Patient patient;

    @OneToOne(mappedBy = "account")
    @JsonBackReference(value="doctor")
    private Doctor doctor;

    public String getName() {
        return name;
    }

    public Account(String name, long amount) {
        this.name = name;
        this.amount = amount;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void update(Account account) {
        if(account.amount != this.amount)
            this.amount = account.amount;
        if(account.name != null)
            this.name = account.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Account() {

    }

}
