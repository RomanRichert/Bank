package com.telran.bank.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.iban4j.Iban.random;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Column(name = "id")
    @Id
    private final String id = random().toFormattedString();

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Invalid email")
    @Setter
    @Column(name = "email")
    private String email;

    @Column(name = "creation_date")
    private final LocalDate creationDate = LocalDate.now();

    @Setter
    @NotBlank(message = "First name should not be blank")
    @Size(min = 1, max = 1478, message = "First name should be between 1 and 1478 characters")
    @Column(name = "first_name")
    private String firstName;

    @Setter
    @NotBlank(message = "Last name should not be blank")
    @Size(min = 1, max = 700, message = "Last name should be between 1 and 700 characters")
    @Column(name = "last_name")
    private String lastName;

    @Setter
    @NotBlank(message = "Country should not be blank")
    @Size(min = 3, max = 56, message = "Country should be between 3 and 56 characters")
    @Column(name = "country")
    private String country;

    @Setter
    @NotBlank(message = "City should not be blank")
    @Size(min = 1, max = 180, message = "City should be between 1 and 180 characters")
    @Column(name = "city")
    private String city;

    @Setter
    @Column(name = "amount_of_money")
    private BigDecimal amountOfMoney = BigDecimal.valueOf(100);

    @ManyToMany
    private Set<Transaction> transactions = new LinkedHashSet<>();

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
                .collect(toSet());
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
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