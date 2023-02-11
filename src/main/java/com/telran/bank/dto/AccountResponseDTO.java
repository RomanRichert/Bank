package com.telran.bank.dto;

import lombok.Value;

import java.util.List;

@Value()
public class AccountResponseDTO {
    String id;

    String email;

    String creationDate;

    String firstName;

    String lastName;

    String country;

    String city;

    String amountOfMoney;

    List<String> transactions;
}