package com.telran.bank.exception;

import static com.telran.bank.exception.messages.ErrorMessage.NOT_ENOUGH_MONEY;

public class NotEnoughMoneyException extends BadRequestException {

    public NotEnoughMoneyException(String message) {
        super(NOT_ENOUGH_MONEY.getMessage() + message);
    }
}