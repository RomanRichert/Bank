package com.telran.bank.entity;


import com.telran.bank.entity.enums.TransactionType;
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
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_time")
    private final LocalTime creationTime = LocalTime.now();

    @Column(name = "date_time")
    private final LocalDate dateTime = LocalDate.now();

    @Column(name = "type")
    private TransactionType type;

    @Column(name = "account_from")
    private Long accountFrom;

    @Column(name = "account_to")
    private Long accountTo;

    @NotNull(message = "Amount should be between 1 and 3000")
    @Min(value = -1000, message = "Amount should be between 1 and 3000")
    @Max(value = 3000, message = "Amount should be between 1 and 3000")
    @Column(name = "amount")
    private BigDecimal amount;

    public Transaction(TransactionType type, Long accountFrom, Long accountTo, Double amount) {
        this.type = type;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = BigDecimal.valueOf(amount);
    }
}