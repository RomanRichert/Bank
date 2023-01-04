package com.telran.bank.controller;

import com.telran.bank.Entity.Transaction;
import com.telran.bank.Enum.TransactionType;
import com.telran.bank.Exception.TransactionNotFoundException;

import com.telran.bank.service.AccountService;
import com.telran.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Validated
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/transactions")
    @Transactional
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions(@RequestParam(required = false) String date,
                                        @RequestParam(required = false) String type,
                                        @RequestParam(required = false) String sort) {
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
                               @RequestParam Double amount){

        Transaction transaction = transactionService.saveTransaction(new Transaction(TransactionType.MONEY_TRANSFER.toString(), from, to, amount));

        accountService.putTransaction(from, to, amount, transaction);

        return transaction;
    }
}
