package com.telran.bank.util;

import com.telran.bank.entity.Account;
import com.telran.bank.entity.Transaction;
import lombok.experimental.UtilityClass;

import static com.telran.bank.entity.enums.TransactionType.*;

@UtilityClass
public class EntityCreator {

    public static final Account ACCOUNT = new Account(
                "email@email.com",
                "FirstName",
                "LastName",
                "Country",
                "City"
        );

    public static final Transaction TRANSACTION_MONEY_TRANSFER = new Transaction(MONEY_TRANSFER, "1", "2", 26.0);

    public static final Transaction TRANSACTION_ATM_WITHDRAW = new Transaction(ATM_WITHDRAW, "1", "1", 100.11);

    public static final Transaction TRANSACTION_ATM_DEPOSIT = new Transaction(ATM_DEPOSIT, "2", "2", 200.22);
}