package com.telran.bank.controller;

import com.telran.bank.dto.AccountDTO;
import com.telran.bank.exception.BankAccountNotFoundException;
import com.telran.bank.service.impl.AccountServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Api("Controller for managing accounts")
public class AccountController {
    private final AccountServiceImpl accountServiceImpl;

    private static final String ID = "/{id}";

    @ApiOperation("Creating a new account")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO createAccount(@RequestBody AccountDTO accountDTO) {

        return accountServiceImpl.saveAccount(accountDTO);
    }

    @ApiOperation("Getting all accounts with given date, city and a way to sort\n Getting all accounts if no params entered")
    @GetMapping()
    public List<AccountDTO> getAllAccounts(@RequestParam(required = false) String date,
                                           @RequestParam(required = false) List<String> city,
                                           @RequestParam(required = false) String sort) {

        return accountServiceImpl.getAllAccounts(date, city, sort);
    }

    @ApiOperation("Getting an existing account by id")
    @GetMapping(ID)
    public AccountDTO getAccount(@PathVariable String id) throws BankAccountNotFoundException {

        return accountServiceImpl.getAccount(id);
    }

    @ApiOperation("Updating an existing account by id")
    @PatchMapping(ID)
    public AccountDTO patchAccount(@PathVariable String id,
                                   @RequestBody AccountDTO accountDTO) throws BankAccountNotFoundException {

        return accountServiceImpl.editAccount(id, accountDTO);
    }
}