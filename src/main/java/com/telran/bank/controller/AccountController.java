package com.telran.bank.controller;

import com.telran.bank.Exception.BankAccountNotFoundException;
import com.telran.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.telran.bank.Entity.Account;


import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts(@RequestParam(required = false) String creationDate,
                                        @RequestParam(required = false) List<String> city,
                                        @RequestParam(required = false) String sort) {
        return accountService.getAllAccounts(creationDate, city, sort);
    }

    @GetMapping("/accounts/{id}")
    public Account getAccount(@PathVariable Long id) throws BankAccountNotFoundException {
        return accountService.getAccount(id);
    }
    @PatchMapping("/accounts/{id}")
    public Account patchAccount(@PathVariable Long id,
                                @RequestBody Account account){
        return accountService.editAccount(id, account);
    }
  @DeleteMapping("/accounts/{id}")
    public void deleteAccount(@PathVariable Long id){
        accountService.deleteAccaunt(id);
    }
}
