package com.telran.bank.util;

import com.telran.bank.entity.Account;
import com.telran.bank.entity.Transaction;
import lombok.experimental.UtilityClass;

import static com.telran.bank.entity.enums.TransactionType.*;

@UtilityClass
public class EntityCreator {

    public static final Account ACCOUNT1 = new Account(
                "email@email.com",
                "FirstName",
                "LastName",
                "Country",
                "City"
        );

    public static final Account ACCOUNT2 = new Account(
                "account2@ac2.com",
                "Number",
                "Two",
                "Countryland",
                "Cityburg"
        );

    public static final Transaction TRANSACTION_MONEY_TRANSFER = new Transaction(MONEY_TRANSFER, ACCOUNT1.getId(), ACCOUNT2.getId(), 26.0);

    public static final Transaction TRANSACTION_ATM_WITHDRAW = new Transaction(ATM_WITHDRAW, ACCOUNT1.getId(), ACCOUNT1.getId(), 100.11);

    public static final Transaction TRANSACTION_ATM_DEPOSIT = new Transaction(ATM_DEPOSIT, ACCOUNT2.getId(), ACCOUNT2.getId(), 200.22);
}