package com.telran.bank.controller;

import com.telran.bank.dto.AccountRequestDTO;
import com.telran.bank.dto.AccountResponseDTO;
import com.telran.bank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Tag(name = "Controller for managing accounts")
public class AccountController {
    private final AccountService accountService;

    private static final String ID = "/{id}";

    @Operation(summary = "Creating a new account")
    @PostMapping()
    @ResponseStatus(CREATED)
    public AccountResponseDTO createAccount(@RequestBody @Valid AccountRequestDTO accountRequestDTO) {
        return accountService.saveAccount(accountRequestDTO);
    }


    @Operation(summary = "Getting all accounts with given date, city and a way to sort. Getting all accounts if no params entered")
    @GetMapping()
    public List<AccountResponseDTO> getAllAccounts(@RequestParam(required = false) String date,
                                                   @RequestParam(required = false) List<String> city,
                                                   @RequestParam(required = false) String sort) {
        return accountService.getAllAccounts(date, city, sort);
    }

    @Operation(summary = "Getting an existing account by id")
    @GetMapping(ID)
    public AccountResponseDTO getAccount(@PathVariable String id) {
        return accountService.getAccount(id);
    }

    @Operation(summary = "Updating an existing account by id")
    @PatchMapping(ID)
    public AccountResponseDTO patchAccount(@PathVariable String id,
                                           @RequestBody AccountRequestDTO accountRequestDTO) {
        return accountService.editAccount(id, accountRequestDTO);
    }
}