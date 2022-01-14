package demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="phone", nullable = false)
    private String phone;

    @Column(name="age", nullable = false)
    private String age;

    @Column(name="medical_code", nullable = false)
    private long medicalCode;

    @Column(name="sex", nullable = false)
    private String sex;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @OneToMany(targetEntity = Disease.class, fetch = FetchType.EAGER, mappedBy = "patient")
    @JsonManagedReference
    private Set<Disease> diseases;

    @OneToMany(targetEntity = Appointment.class, fetch = FetchType.EAGER, mappedBy = "patient")
    @JsonManagedReference(value="getAppointments")
    private Set<Appointment> appointments;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="account_id", referencedColumnName = "id", nullable = true)
    @JsonManagedReference(value="patient")
    private Account account;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(Set<Disease> diseases) {
        this.diseases = diseases;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Patient() {

    }

    public Patient(long id, String firstName, String lastName, String email, String password, String phone, String age, long medicalCode, String sex) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.age = age;
        this.medicalCode = medicalCode;
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public long getMedicalCode() {
        return medicalCode;
    }

    public void setMedicalCode(long medicalCode) {
        this.medicalCode = medicalCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Patient(
            String firstName, String lastName, String email, String password, String phone, String age,
            long medicalCode, String sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.age = age;
        this.medicalCode = medicalCode;
        this.sex = sex;
    }

    public void update(Patient patient) {
        if(patient.firstName != null)
            this.firstName = patient.firstName;
        if(patient.lastName != null)
            this.lastName = patient.lastName;
        if(patient.email != null)
            this.email = patient.email;
        if(patient.password != null)
            this.password = patient.password;
        if(patient.phone != null)
            this.phone = patient.phone;
        if(patient.age != null)
            this.age = patient.age;
        if(patient.medicalCode != this.medicalCode)
            this.medicalCode = patient.medicalCode;
        if(patient.sex != null)
            this.sex = patient.sex;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void addDisease(Disease disease) {
        this.diseases.add(disease);
    }

    public void removeAccount() { this.account = null; }

    public void removeAppointments(Appointment app){
        this.appointments.remove(app);
    }

    public void removeDisease(Disease disease){
        this.diseases.remove(disease);
    }

}
