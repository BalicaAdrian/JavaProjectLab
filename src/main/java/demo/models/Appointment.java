package demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="start_time", nullable = false)
    private Date startTime;

    @Column(name="end_time", nullable = true)
    private Date endTime;

    @Column(name="canceled", nullable = false)
    private boolean canceled;

    @Column(name="priority", nullable = false)
    private boolean priority;

    @ManyToOne()
    @JoinColumn(name = "patient", referencedColumnName = "id")
    @JsonBackReference(value="getAppointments")
    private Patient patient;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @ManyToOne()
    @JoinColumn(name = "doctor", referencedColumnName = "id")
    @JsonBackReference(value =  "get_doctor")
    private Doctor doctor;

    public Appointment(Date startTime, Date endTime, boolean canceled, boolean priority) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.canceled = canceled;
        this.priority = priority;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void update(Appointment appointment) {
        if(appointment.canceled != this.canceled)
            this.canceled = appointment.canceled;
        if(appointment.priority != this.priority)
            this.priority = appointment.priority;
        if(appointment.endTime != null)
            this.endTime = appointment.endTime;
        if(appointment.startTime != null)
            this.startTime = appointment.startTime;
    }


    public Appointment() {

    }

    public Date getStartTime() {
        return startTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public void removeDoctor() {
        this.doctor = null;
    }

    public void removePatient() {
        this.patient = null;
    }

}
