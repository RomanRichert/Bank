package com.telran.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value()
public class AccountDTO {
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Invalid email")
    @JsonProperty("email")
    String email;
    @NotBlank(message = "First name should not be blank")
    @Size(min = 1, max = 1478, message = "First name should be between 1 and 1478 characters")
    @JsonProperty("firstName")
    String firstName;
    @NotBlank(message = "Last name should not be blank")
    @Size(min = 1, max = 700, message = "Last name should be between 1 and 700 characters")
    @JsonProperty("lastName")
    String lastName;
    @NotBlank(message = "Country should not be blank")
    @Size(min = 3, max = 56, message = "Country should be between 3 and 56 characters")
    @JsonProperty("country")
    String country;
    @NotBlank(message = "City should not be blank")
    @Size(min = 1, max = 180, message = "City should be between 1 and 180 characters")
    @JsonProperty("city")
    String city;
}
