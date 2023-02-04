package com.telran.bank.controller;

import com.telran.bank.dto.AccountDTO;
import com.telran.bank.exception.BankAccountNotFoundException;
import com.telran.bank.mapper.AccountMapper;
import com.telran.bank.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountServiceImpl accountServiceImpl;

    private final AccountMapper accountMapper;

    private static final String ID = "/{id}";

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO createAccount(@RequestBody AccountDTO accountDTO) {

        return accountServiceImpl.saveAccount(accountMapper.toEntity(accountDTO));
    }

    @GetMapping()
    public List<AccountDTO> getAllAccounts(@RequestParam(required = false) String date,
                                        @RequestParam(required = false) List<String> city,
                                        @RequestParam(required = false) String sort) {

        return accountServiceImpl.getAllAccounts(date, city, sort);
    }

    @GetMapping(ID)
    public AccountDTO getAccount(@PathVariable Long id) throws BankAccountNotFoundException {

        return accountServiceImpl.getAccount(id);
    }

    @PatchMapping(ID)
    public AccountDTO patchAccount(@PathVariable Long id,
                                @RequestBody AccountDTO accountDTO) throws BankAccountNotFoundException {

        return accountServiceImpl.editAccount(id, accountMapper.toEntity(accountDTO));
    }
}