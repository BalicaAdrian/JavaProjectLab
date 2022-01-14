package demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="age", nullable = false)
    private int age;

    @Column(name="speciality", nullable = false)
    private String speciality;

    @Column(name="sex", nullable = false)
    private String sex;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="phone", nullable = false)
    private String phone;

    @OneToMany(targetEntity = Appointment.class, fetch = FetchType.EAGER, mappedBy = "doctor")
    @JsonManagedReference(value =  "get_doctor")
    private Set<Appointment> appointments;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="account_id", referencedColumnName = "id", nullable = true)
    @JsonManagedReference(value="doctor")
    private Account account;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Doctor(String firstName, String lastName, int age, String speciality, String sex, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.speciality = speciality;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
    }

    public void update(Doctor doctor) {
        if(doctor.firstName != null)
            this.firstName = doctor.firstName;
        if(doctor.lastName != null)
            this.lastName = doctor.lastName;
        if(doctor.age != this.age)
            this.age = doctor.age;
        if(doctor.speciality != null)
            this.speciality = doctor.speciality;
        if(doctor.sex != null)
            this.sex = doctor.sex;
        if(doctor.email != null)
            this.email = doctor.email;
        if(doctor.phone != null)
            this.phone = doctor.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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

    public void deleteAccount() {
        this.account = null;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Doctor() {

    }
}
