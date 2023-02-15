package com.telran.bank.dto;

import lombok.ToString;
import lombok.Value;

@Value()
@ToString
public class AccountRequestDTO {
    String email;

    String firstName;

    String lastName;

    String country;

    String city;
}