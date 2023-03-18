package com.telran.bank.controller;

import com.telran.bank.dto.TransactionDTO;
import com.telran.bank.service.AccountService;
import com.telran.bank.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Controller for managing transactions")
public class TransactionController {

    private final TransactionService transactionService;

    private final AccountService accountService;

    @ResponseStatus(OK)
    @ApiResponse(responseCode = "200", description = "Successfully returned list of transactions", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TransactionDTO.class)))
    })
    @Operation(summary = "Request for all transactions", description = "Getting all transactions with given date, type and a way to sort. Getting all transactions if no params entered")
    @GetMapping("/transactions")
    public List<TransactionDTO> getAllTransactions(@RequestParam(required = false) String date,
                                                   @RequestParam(required = false) String type,
                                                   @RequestParam(required = false) String sort) {
        return transactionService.getAllTransactions(date, type, sort);
    }

    @ResponseStatus(OK)
    @GetMapping("/transactions/{id}")
    @ApiResponse(responseCode = "200", description = "Successfully returned the transaction", content = {
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TransactionDTO.class))
    })
    @Operation(summary = "Request for a specific transaction", description = "Getting an existing transaction by id")
    public TransactionDTO getTransaction(@PathVariable Long id) {
        return transactionService.getTransaction(id);
    }

    @ResponseStatus(OK)
    @PutMapping("/accounts")
    @ApiResponse(responseCode = "200", description = "Transaction was successful")
    @Operation(summary = "Making transactions between accounts", description = "Requires the IBANs from sender end recipient and amount of money to make a transaction. " +
            "If the IBANs are equal and the amount is negative, than the type of the transaction will be \"ATM_WITHDRAW\". " +
            "If the IBANs are equal and the amount is positive, than the type of the transaction will be \"ATM_DEPOSIT\". " +
            "If the IBANs are different and the amount is positive, than the type of the transaction will be \"MONEY_TRANSFER\".")
    public void putTransaction(@RequestParam String from,
                               @RequestParam String to,
                               @RequestParam Double amount) {
        accountService.putTransaction(from, to, amount);
    }
}