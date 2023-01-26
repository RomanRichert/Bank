package com.telran.bank.controller;

import com.telran.bank.entity.Transaction;
import com.telran.bank.enums.TransactionType;
import com.telran.bank.exception.BadRequestException;
import com.telran.bank.exception.BankAccountNotFoundException;
import com.telran.bank.exception.NotEnoughMoneyException;
import com.telran.bank.exception.TransactionNotFoundException;

import com.telran.bank.service.impl.AccountServiceImpl;
import com.telran.bank.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@RestController
@Validated
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImpl transactionServiceImpl;
    private final AccountServiceImpl accountServiceImpl;

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions(@RequestParam(required = false) String date,
                                                @RequestParam(required = false) String type,
                                                @RequestParam(required = false) String sort) throws ParseException {
        return transactionServiceImpl.getAllTransactions(date, type, sort);
    }

    @GetMapping("/transactions/{id}")
    public Transaction getTransaction(@PathVariable Long id) throws TransactionNotFoundException {
        return transactionServiceImpl.getTransaction(id);
    }

    @Transactional
    @PutMapping("/accounts")
    public Transaction putTransaction(@RequestParam Long from,
                                      @RequestParam Long to,
                                      @RequestParam Double amount) throws BankAccountNotFoundException, NotEnoughMoneyException, BadRequestException {
        if (amount == 0) throw new BadRequestException("Amount shouldn't be 0");

        Transaction transaction;

        if (Objects.equals(from, to)) {
            if (amount < 0) {
                transaction = transactionServiceImpl.saveTransaction(new Transaction(TransactionType.ATM_WITHDRAW, from, to, amount));
            } else {
                transaction = transactionServiceImpl.saveTransaction(new Transaction(TransactionType.ATM_DEPOSIT, from, to, amount));
            }
        } else {
            transaction = transactionServiceImpl.saveTransaction(new Transaction(TransactionType.MONEY_TRANSFER, from, to, amount));
        }


        accountServiceImpl.putTransaction(from, to, amount, transaction);

        return transaction;
    }
}
