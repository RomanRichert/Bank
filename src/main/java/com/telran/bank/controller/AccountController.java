package com.telran.bank.controller;

import com.telran.bank.dto.AccountDTO;
import com.telran.bank.entity.Account;
import com.telran.bank.exception.BankAccountNotFoundException;
import com.telran.bank.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountServiceImpl accountServiceImpl;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Account createAccount(@RequestBody AccountDTO account) {
        return accountServiceImpl.saveAccount(convertAccountDtoToAccountEntity(account));
    }

    @GetMapping()
    public List<Account> getAllAccounts(@RequestParam(required = false) String date,
                                        @RequestParam(required = false) List<String> city,
                                        @RequestParam(required = false) String sort) {
        return accountServiceImpl.getAllAccounts(date, city, sort);
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) throws BankAccountNotFoundException {
        return accountServiceImpl.getAccount(id);
    }

    @PatchMapping("/{id}")
    @Transactional
    public Account patchAccount(@PathVariable Long id,
                                @RequestBody AccountDTO account) throws BankAccountNotFoundException {
        return accountServiceImpl.editAccount(id, convertAccountDtoToAccountEntity(account));
    }

    private Account convertAccountDtoToAccountEntity(AccountDTO accountDTO){
        return new Account(
                accountDTO.getEmail(),
                accountDTO.getFirstName(),
                accountDTO.getLastName(),
                accountDTO.getCountry(),
                accountDTO.getCity()
        );
    }
}
