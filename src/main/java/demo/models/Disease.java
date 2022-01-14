package demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="disease")
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="day_of_treatments", nullable = false)
    private int dayOfTreatments;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="price", nullable = false)
    private long price;

    @ManyToOne()
    @JoinColumn(name = "patient", referencedColumnName = "id")
    @JsonBackReference
    private Patient patient;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "medicamentation",
            joinColumns = @JoinColumn(name = "disease_id"),
            inverseJoinColumns = @JoinColumn(name = "med_id"))
    private Set<Med> meds;

    public Disease() {

    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Disease(String name, int dayOfTreatments, Date startDate, long price) {
        this.name = name;
        this.dayOfTreatments = dayOfTreatments;
        this.startDate = startDate;
        this.price = price;
    }
    public void update(Disease disease) {
        if(disease.dayOfTreatments != this.dayOfTreatments)
            this.dayOfTreatments = disease.dayOfTreatments;
        if(disease.name != null)
            this.name = disease.name;
        if(disease.price != this.price)
            this.price = disease.price;
        if(disease.startDate != null)
            this.startDate = disease.startDate;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDayOfTreatments() {
        return dayOfTreatments;
    }

    public void setDayOfTreatments(int dayOfTreatments) {
        this.dayOfTreatments = dayOfTreatments;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public long getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Set<Med> getMeds() {
        return meds;
    }

    public void setMeds(Set<Med> meds) {
        this.meds = meds;
    }

    public void addMeds(Med med){
        this.meds.add(med);
    }

    public void removeMed(Med med){
        this.meds.remove(med);
    }

}
