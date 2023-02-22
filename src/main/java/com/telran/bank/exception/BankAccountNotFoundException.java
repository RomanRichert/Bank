package com.telran.bank.exception;

import static com.telran.bank.exception.messages.ErrorMessage.BANK_ACCOUNT_NOT_FOUND;

public class BankAccountNotFoundException extends EntityNotFoundException {

    public BankAccountNotFoundException(String id) {
        super(BANK_ACCOUNT_NOT_FOUND.getMessage() + id);
    }
}