package com.telran.bank.dto;

import lombok.Value;

import java.util.List;

@Value()
public class AccountDTO {
    String email;

    String firstName;

    String lastName;

    String country;

    String city;

    String amountOfMoney;

    List<String> transactions;
}