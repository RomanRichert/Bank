package com.telran.bank.controller;

import com.telran.bank.Entity.Transaction;
import com.telran.bank.Exception.TransactionNotFoundException;

import com.telran.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
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
}
