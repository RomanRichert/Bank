package com.telran.bank.Entity;




import com.telran.bank.Enum.TransactionType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final LocalDateTime dateTime = LocalDateTime.now();

    private TransactionType type;
    @Size(min = 20, max = 20, message = "Invalid IBAN")
    @Digits(integer = 20, fraction = 0, message = "Invalid IBAN")
    private String accountFrom;
    @Size(min = 20, max = 20, message = "Invalid IBAN")
    @Digits(integer = 20, fraction = 0, message = "Invalid IBAN")
    private String accountTo;
    @NotNull(message = "Amount should be between 1 and 3000")
    @Min(value = 1,message = "Amount should be between 1 and 3000")
    @Max(value = 3000, message = "Amount should be between 1 and 3000")
    private BigDecimal amount;

    public Transaction() {
    }

    public Transaction(String type, String accountFrom, String accountTo, Double amount) {
        this.type = TransactionType.valueOf(type);
        this.accountFrom=accountFrom;
        this.accountTo=accountTo;
        this.amount = BigDecimal.valueOf(amount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountFrom() {
        return "DE"+accountFrom;
    }

    public String getAccountTo() {
        return "DE"+accountTo;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
