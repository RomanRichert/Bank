package com.telran.bank.controller;

import com.telran.bank.dto.TransactionDTO;
import com.telran.bank.exception.BadRequestException;
import com.telran.bank.exception.BankAccountNotFoundException;
import com.telran.bank.exception.NotEnoughMoneyException;
import com.telran.bank.exception.TransactionNotFoundException;

import com.telran.bank.service.impl.AccountServiceImpl;
import com.telran.bank.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImpl transactionServiceImpl;
    private final AccountServiceImpl accountServiceImpl;

    @GetMapping("/transactions")
    public List<TransactionDTO> getAllTransactions(@RequestParam(required = false) String date,
                                                   @RequestParam(required = false) String type,
                                                   @RequestParam(required = false) String sort) {
        return transactionServiceImpl.getAllTransactions(date, type, sort);
    }

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) throws TransactionNotFoundException {
        return transactionServiceImpl.getTransaction(id);
    }

    @PutMapping("/accounts")
    public void putTransaction(@RequestParam Long from,
                                      @RequestParam Long to,
                                      @RequestParam Double amount) throws BankAccountNotFoundException, NotEnoughMoneyException, BadRequestException {
        accountServiceImpl.putTransaction(from, to, amount);
    }
}