package com.telran.bank.controller;

import com.telran.bank.dto.TransactionDTO;
import com.telran.bank.exception.BadRequestException;
import com.telran.bank.exception.BankAccountNotFoundException;
import com.telran.bank.exception.TransactionNotFoundException;
import com.telran.bank.service.AccountService;
import com.telran.bank.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Tag(name = "Controller for managing transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @Operation(summary = "Getting all transactions with given date, type and a way to sort. Getting all transactions if no params entered")
    @GetMapping("/transactions")
    public List<TransactionDTO> getAllTransactions(@RequestParam(required = false) String date,
                                                   @RequestParam(required = false) String type,
                                                   @RequestParam(required = false) String sort) {
        return transactionService.getAllTransactions(date, type, sort);
    }

    @Operation(summary = "Getting an existing transaction by id")
    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) throws TransactionNotFoundException {
        return transactionService.getTransaction(id);
    }

    @Operation(summary = "Making transactions between accounts")
    @PutMapping("/accounts")
    public void putTransaction(@RequestParam String from,
                                      @RequestParam String to,
                                      @RequestParam Double amount) throws BankAccountNotFoundException, BadRequestException {
        accountService.putTransaction(from, to, amount);
    }
}