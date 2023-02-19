package com.telran.bank.exception;

import static com.telran.bank.exception.enums.messages.ErrorMessage.INVALID_TRANSACTION_TYPE;

public class InvalidTransactionTypeException extends RuntimeException {
    public InvalidTransactionTypeException(String type) {
        super(type + INVALID_TRANSACTION_TYPE.getMessage());
    }
}
