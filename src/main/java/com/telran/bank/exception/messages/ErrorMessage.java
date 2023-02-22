package com.telran.bank.exception.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.telran.bank.entity.enums.TransactionType.*;

@AllArgsConstructor
@Getter
public enum ErrorMessage {

    NOT_ENOUGH_MONEY("Not enough money on the account. id = "),
    BANK_ACCOUNT_NOT_FOUND("Bank account not found. id = "),
    AMOUNT_IS_0("Amount shouldn't be 0. "),
    TRANSFER_AMOUNT_IS_NEGATIVE("Transfer amount can't be negative. "),
    INVALID_TRANSACTION_TYPE(" this transaction type doesn't exist. Available types: " + ATM_DEPOSIT + ", " + ATM_WITHDRAW + ", " + MONEY_TRANSFER),
    INVALID_DATE(" bad date format. Please enter the date in following format yyyy-MM-dd"),
    TRANSACTION_NOT_FOUND("Transaction not found. ");

    private final String message;
}