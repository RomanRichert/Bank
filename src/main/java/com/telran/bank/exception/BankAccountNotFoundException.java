package com.telran.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BankAccountNotFoundException extends EntityNotFoundException {

    public BankAccountNotFoundException(String message) {
        super(ErrorMessage.BANK_ACCOUNT_NOT_FOUND.getMessage()+message);
    }
}