package com.telran.bank.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
    NOT_ENOUGH_MONEY("Not enough money on the account. "),
    BANK_ACCOUNT_NOT_FOUND("Bank account not found. "),
    AMOUNT_IS_0("Amount shouldn't be 0. "),
    TRANSFER_AMOUNT_IS_NEGATIVE("Transfer amount can't be negative. "),
    TRANSACTION_NOT_FOUND("Transaction not found. ");

    private final String message;
}