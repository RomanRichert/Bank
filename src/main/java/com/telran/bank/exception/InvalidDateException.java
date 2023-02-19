package com.telran.bank.exception;

import static com.telran.bank.exception.enums.messages.ErrorMessage.INVALID_DATE;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String date){
        super(date + INVALID_DATE.getMessage());
    }
}
