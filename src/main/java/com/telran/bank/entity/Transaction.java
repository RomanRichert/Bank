package com.telran.bank.entity;


import com.telran.bank.enums.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "Transactions")
public class Transaction {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private final LocalTime creationTime = LocalTime.now();
    @Column
    private final LocalDate dateTime = LocalDate.now();
    @Column
    private TransactionType type;
    @Column
    private Long accountFrom;
    @Column
    private Long accountTo;
    @NotNull(message = "Amount should be between 1 and 3000")
    @Min(value = 1, message = "Amount should be between 1 and 3000")
    @Max(value = 3000, message = "Amount should be between 1 and 3000")
    private BigDecimal amount;

    public Transaction(TransactionType type, Long accountFrom, Long accountTo, Double amount) {
        this.type = type;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = BigDecimal.valueOf(amount);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = TransactionType.valueOf(type);
    }
}
