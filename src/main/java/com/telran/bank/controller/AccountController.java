package com.telran.bank.controller;

import com.telran.bank.exception.BankAccountNotFoundException;
import com.telran.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.telran.bank.entity.Account;


import javax.transaction.Transactional;
import java.util.List;

@RestController
@Validated
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Account createAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts(@RequestParam(required = false) String date,
                                        @RequestParam(required = false) List<String> city,
                                        @RequestParam(required = false) String sort) {
        return accountService.getAllAccounts(date, city, sort);
    }

    @GetMapping("/accounts/{id}")
    public Account getAccount(@PathVariable Long id) throws BankAccountNotFoundException {
        return accountService.getAccount(id);
    }

    @PatchMapping("/accounts/{id}")
    @Transactional
    public Account patchAccount(@PathVariable Long id,
                                @RequestBody Account account) throws BankAccountNotFoundException {
        return accountService.editAccount(id, account);
    }
}
