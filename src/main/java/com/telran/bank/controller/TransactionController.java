package com.telran.bank.controller;

import com.telran.bank.dto.TransactionDTO;
import com.telran.bank.exception.BadRequestException;
import com.telran.bank.exception.BankAccountNotFoundException;
import com.telran.bank.exception.NotEnoughMoneyException;
import com.telran.bank.exception.TransactionNotFoundException;

import com.telran.bank.service.impl.AccountServiceImpl;
import com.telran.bank.service.impl.TransactionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Api("Controller for managing transactions")
public class TransactionController {
    private final TransactionServiceImpl transactionServiceImpl;
    private final AccountServiceImpl accountServiceImpl;

    @ApiOperation("Getting all transactions with given date, type and a way to sort\n Getting all transactions if no params entered")
    @GetMapping("/transactions")
    public List<TransactionDTO> getAllTransactions(@RequestParam(required = false) String date,
                                                   @RequestParam(required = false) String type,
                                                   @RequestParam(required = false) String sort) {
        return transactionServiceImpl.getAllTransactions(date, type, sort);
    }

    @ApiOperation("Getting an existing transaction by id")
    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) throws TransactionNotFoundException {
        return transactionServiceImpl.getTransaction(id);
    }

    @ApiOperation("Making transactions between accounts")
    @PutMapping("/accounts")
    public void putTransaction(@RequestParam String from,
                                      @RequestParam String to,
                                      @RequestParam Double amount) throws BankAccountNotFoundException, NotEnoughMoneyException, BadRequestException {
        accountServiceImpl.putTransaction(from, to, amount);
    }
}