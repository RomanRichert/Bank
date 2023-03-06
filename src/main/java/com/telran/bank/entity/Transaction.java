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

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "creation_time")
    private final LocalTime creationTime = LocalTime.now();

    @Column(name = "creation_date")
    private final LocalDate creationDate = LocalDate.now();

    @Column(name = "type")
    private TransactionType type;

    @Column(name = "account_from")
    private String accountFrom;

    @Column(name = "account_to")
    private String accountTo;

    @NotNull(message = "Amount expected between -1000 and 3000. Actual: null")
    @Min(value = -1000, message = "Amount expected between -1000 and 3000. Actual: <-1000")
    @Max(value = 3000, message = "Amount expected between -1000 and 3000. Actual: >3000")
    @Column(name = "amount")
    private BigDecimal amount;

    public Transaction(TransactionType type, String accountFrom, String accountTo, Double amount) {
        this.type = type;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = BigDecimal.valueOf(amount);
    }
}