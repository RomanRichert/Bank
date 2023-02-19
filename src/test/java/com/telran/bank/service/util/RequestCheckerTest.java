package com.telran.bank.service.util;

import com.telran.bank.entity.Account;
import com.telran.bank.exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.telran.bank.util.EntityCreator.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RequestCheckerTest {
    @Test
    void checkAccount() {
        assertThrows(BankAccountNotFoundException.class, () -> RequestChecker.checkAccount(null, "random"), "BankAccountNotFoundException should be thrown");
    }

    @Test
    void checkDate() {
        assertThrows(InvalidDateException.class, () -> RequestChecker.checkDate("Not in format of yyyy-MM-dd"), "InvalidDateException should be thrown");
    }

    @Test
    void checkTransactionType() {
        assertThrows(InvalidTransactionTypeException.class, () -> RequestChecker.checkTransactionType("Not a TransactionType"), "InvalidTransactionTypeException should be thrown");
    }

    @Test
    void checkTransactionPossibility() {
        String fromId = ACCOUNT1.getId();
        String toId = ACCOUNT2.getId();
        Account accountFrom = ACCOUNT1;
        Account accountTo = ACCOUNT2;

        assertThrows(BadRequestException.class, () -> RequestChecker.checkTransactionPossibility(fromId, toId, 0.0, accountFrom, accountTo), "The amount in the transaction is 0. BadRequestException should be thrown");
        assertThrows(NotEnoughMoneyException.class, () -> RequestChecker.checkTransactionPossibility(fromId, toId, 3000.0, accountFrom, accountTo), "The amount is bigger than the accounts balance, money-transfer is impossible. NotEnoughMoneyException should be thrown");
        assertThrows(NotEnoughMoneyException.class, () -> RequestChecker.checkTransactionPossibility(fromId, fromId, -3000.0, accountFrom, accountFrom), "The amount is bigger than the accounts balance, withdraw via ATM is impossible. NotEnoughMoneyException should be thrown");
        assertThrows(BadRequestException.class, () -> RequestChecker.checkTransactionPossibility(fromId, toId, -3000.0, accountFrom, accountTo), "The amount in the transaction is negative, money-transfer is impossible. BadRequestException should be thrown");
    }
}