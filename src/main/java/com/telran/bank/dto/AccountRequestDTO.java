package com.telran.bank.dto;

import lombok.Value;

@Value()
public class AccountRequestDTO {
    String email;

    String firstName;

    String lastName;

    String country;

    String city;
}