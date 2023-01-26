package com.telran.bank.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private final LocalDate creationDate = LocalDate.now();
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String country;
    @Column
    private String city;
    @Column
    private BigDecimal amountOfMoney = BigDecimal.valueOf(100);
    @Column
    @ManyToMany
    @ToString.Exclude
    private Set<Transaction> transactions = new LinkedHashSet<>();

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Account(String email, String firstName, String lastName, String country, String city) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
    }

    public Set<Long> getTransactions() {
        return transactions.stream()
                .map(Transaction::getId)
                .collect(Collectors.toSet());
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAmountOfMoney(Double amount) {
        this.amountOfMoney = this.amountOfMoney.add(BigDecimal.valueOf(amount));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(email, account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
