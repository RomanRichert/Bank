package com.telran.bank.entity;

import com.telran.bank.entity.enums.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@ToString
@Transactional
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "creation_time")
    private LocalTime creationTime;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "type")
    private TransactionType type;

    @Column(name = "account_from")
    private String accountFrom;

    @Column(name = "account_to")
    private String accountTo;

    @Column(name = "amount")
    @NotNull(message = "Amount expected between -1000 and 3000. Actual: null")
    @Max(value = 3000, message = "Amount expected between -1000 and 3000. Actual: >3000")
    @Min(value = -1000, message = "Amount expected between -1000 and 3000. Actual: <-1000")
    private BigDecimal amount;

    public Transaction(TransactionType type, String accountFrom, String accountTo, Double amount) {
        this.type = type;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = BigDecimal.valueOf(amount);
        this.creationTime = LocalTime.now();
        this.creationDate = LocalDate.now();
    }
}