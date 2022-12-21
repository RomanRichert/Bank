package com.telran.bank.Entity;




import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final LocalDateTime dateTime = LocalDateTime.now();
    private String type;
    private String accountFrom;
    private String accountTo;
    private int amount;

    public Transaction() {
    }

    public Transaction(String type, String accountFrom, String accountTo, int amount) {
        this.type = type;
        this.accountFrom=accountFrom;
        this.accountTo=accountTo;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

}
