package com.telran.bank.entity;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Invalid email")
    private String email;
    private final LocalDate creationDate = LocalDate.now();
    @NotBlank(message = "First name should not be blank")
    @Size(min = 1, max = 1478, message = "First name should be between 1 and 1478 characters")
    private String firstName;
    @NotBlank(message = "Last name should not be blank")
    @Size(min = 1, max = 700, message = "Last name should be between 1 and 700 characters")
    private String lastName;
    @NotBlank(message = "Country should not be blank")
    @Size(min = 3, max = 56, message = "Country should be between 3 and 56 characters")
    private String country;
    @NotBlank(message = "City should not be blank")
    @Size(min = 1, max = 180, message = "City should be between 1 and 180 characters")
    private String city;
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

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public BigDecimal getAmountOfMoney() {
        return amountOfMoney;
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", creationDate=" + creationDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
