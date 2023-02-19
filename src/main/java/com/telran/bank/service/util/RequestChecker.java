package com.telran.bank.service.util;

import com.telran.bank.entity.Account;
import com.telran.bank.entity.enums.TransactionType;
import com.telran.bank.exception.*;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeParseException;
import java.util.Objects;

import static com.telran.bank.entity.enums.TransactionType.values;
import static com.telran.bank.exception.enums.messages.ErrorMessage.AMOUNT_IS_0;
import static com.telran.bank.exception.enums.messages.ErrorMessage.TRANSFER_AMOUNT_IS_NEGATIVE;
import static java.lang.Math.abs;
import static java.time.LocalDate.parse;

@UtilityClass
public class RequestChecker {
    public static void checkAccount(Account account, String id){
        if (account == null) throw new BankAccountNotFoundException(id);
    }

    public static void checkDate(String date){
        if (date == null) return;

        try{
            parse(date);
        } catch (DateTimeParseException e){
            throw new InvalidDateException(date);
        }
    }

    public static void checkTransactionType(String type){
        if (type == null) return;

        for (TransactionType tt : values()) {
            if(type.equals(tt.name())) return;
        }

        throw new InvalidTransactionTypeException(type);
    }

    public static void checkTransactionPossibility(String fromId, String toId, Double amount, Account fromAccount, Account toAccount){
        if (amount == 0) throw new BadRequestException(AMOUNT_IS_0.getMessage());
        checkAccount(fromAccount, fromId);
        checkAccount(toAccount, toId);
        if (Objects.equals(fromId, toId) && amount < 0 && abs(amount) > fromAccount.getAmountOfMoney().doubleValue()) throw new NotEnoughMoneyException(fromId);

        if (!Objects.equals(fromId, toId) && abs(amount) > fromAccount.getAmountOfMoney().doubleValue()){
            if (amount < 0) throw new BadRequestException(TRANSFER_AMOUNT_IS_NEGATIVE.getMessage());

            throw new NotEnoughMoneyException(fromId);
        }
    }
}