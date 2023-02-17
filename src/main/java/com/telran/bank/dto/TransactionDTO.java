package com.telran.bank.dto;

import lombok.Value;

@Value
public class TransactionDTO {
    String creationTime;

    String creationDate;

    String type;

    String accountFrom;

    String accountTo;

    String amount;
}