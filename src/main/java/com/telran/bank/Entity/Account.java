package com.telran.bank.Entity;




import javax.persistence.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Invalid email")
    private String email;

    private final Date creationDate = new Date(System.currentTimeMillis());

    @NotBlank(message = "First name should not be blank")
    @Size(min = 1, max = 1478, message = "First name should be between 1 and 1478 characters")
    private String firstName;

    @NotBlank(message = "Last name should not be blank")
    @Size(min = 1, max = 700, message = "Last name should be between 1 and 700 characters")
    private String lastName;

    @Size(min = 3,max = 56, message = "Country should be between 3 and 56 characters")
    private String country;

    @Size(min = 1,max = 180, message = "City should be between 1 and 180 characters")
    private String city;
    private BigDecimal amountOfMoney= BigDecimal.valueOf(100);
    @ManyToMany
    private Set<Transaction> transactions = new LinkedHashSet<>();

    public Account(String email, String firstName, String lastName, String country, String city) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
    }

    public Account() {}
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreationDate() {
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
    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransactions(Transaction t){
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
