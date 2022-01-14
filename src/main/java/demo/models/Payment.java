package demo.models;

import javax.persistence.*;

@Entity
@Table(name="payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="amount", nullable = false)
    private long amount;

    @Column(name="already_undo", nullable = false)
    private boolean alreadyUndo;

    @OneToOne
    @JoinColumn(name="receiver_id", referencedColumnName = "id")
    private Account receiver;

    @OneToOne
    @JoinColumn(name="sender_id", referencedColumnName = "id")
    private Account sender;

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Payment(String name, long amount, boolean alreadyUndo) {
        this.name = name;
        this.amount = amount;
        this.alreadyUndo = alreadyUndo;
    }

    public void update(Payment payment) {
        if(payment.name != null)
            this.name = payment.name;
        if(payment.amount != this.amount)
            this.amount = payment.amount;
        if(payment.alreadyUndo != this.alreadyUndo)
            this.alreadyUndo = payment.alreadyUndo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public boolean isAlreadyUndo() {
        return alreadyUndo;
    }

    public void setAlreadyUndo(boolean alreadyUndo) {
        this.alreadyUndo = alreadyUndo;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void deleteAccoounts() {
        this.receiver = null;
        this.sender = null;
    }
    public Payment() {

    }

}
