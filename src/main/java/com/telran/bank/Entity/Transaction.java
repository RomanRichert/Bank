package com.telran.bank.Entity;


import com.telran.bank.Enum.TransactionType;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Transactions")
public class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final LocalTime creationTime = LocalTime.now();
    private final LocalDate dateTime = LocalDate.now();
    private TransactionType type;
    private Long accountFrom;
    private Long accountTo;
    @NotNull(message = "Amount should be between 1 and 3000")
    @Min(value = 1,message = "Amount should be between 1 and 3000")
    @Max(value = 3000, message = "Amount should be between 1 and 3000")
    private BigDecimal amount;

    public Transaction(TransactionType type, Long accountFrom, Long accountTo, Double amount) {
        this.type = type;
        this.accountFrom=accountFrom;
        this.accountTo=accountTo;
        this.amount = BigDecimal.valueOf(amount);
    }

    public Transaction() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public String getDateTime() {
        return dateTime+" "+creationTime;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = TransactionType.valueOf(type);
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
