package com.telran.bank.exception;

import static com.telran.bank.exception.messages.ErrorMessage.TRANSACTION_NOT_FOUND;

public class TransactionNotFoundException extends EntityNotFoundException {

    public TransactionNotFoundException(String message) {
        super(TRANSACTION_NOT_FOUND.getMessage() + message);
    }
}