package com.telran.bank.controller;

import com.telran.bank.Entity.Transaction;
import com.telran.bank.Enum.TransactionType;
import com.telran.bank.Exception.BankAccountNotFoundException;
import com.telran.bank.Exception.NotEnoughMoneyException;
import com.telran.bank.Exception.TransactionNotFoundException;

import com.telran.bank.service.AccountService;
import com.telran.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@RestController
@Validated
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions(@RequestParam(required = false) String date,
                                        @RequestParam(required = false) String type,
                                        @RequestParam(required = false) String sort) throws ParseException {
        return transactionService.getAllTransactions(date, type, sort);
    }

    @GetMapping("/transactions/{id}")
    public Transaction getTransaction(@PathVariable Long id) throws TransactionNotFoundException {
        return transactionService.getTransaction(id);
    }

    @Transactional
    @PutMapping("/accounts")
    public Transaction putTransaction(@RequestParam Long from,
                               @RequestParam Long to,
                               @RequestParam Double amount) throws BankAccountNotFoundException, NotEnoughMoneyException {
        Transaction transaction;
        if(Objects.equals(from, to)){
            transaction = transactionService.saveTransaction(new Transaction(TransactionType.ATM, from, to, amount));
        } else {
            transaction = transactionService.saveTransaction(new Transaction(TransactionType.MONEY_TRANSFER, from, to, amount));
        }


        accountService.putTransaction(from, to, amount, transaction);

        return transaction;
    }
}
