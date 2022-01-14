package demo.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="med")
public class Med {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="quantity", nullable = false)
    private int quantity;

    @Column(name="med_price", nullable = false)
    private long medPrice;

    @ManyToMany(mappedBy = "meds")
    private Set<Disease> diseases;

    public Med() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Med(String name, int quantity, long medPrice) {
        this.name = name;
        this.quantity = quantity;
        this.medPrice = medPrice;
    }

    public Set<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(Set<Disease> diseases) {
        this.diseases = diseases;
    }

    public void update(Med med) {
        if(med.name != null)
            this.name = med.name;
        if(med.medPrice != this.medPrice)
            this.medPrice = med.medPrice;
        if(med.quantity != this.quantity)
            this.quantity = med.quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getMedPrice() {
        return medPrice;
    }

    public void setMedPrice(long medPrice) {
        this.medPrice = medPrice;
    }

}
