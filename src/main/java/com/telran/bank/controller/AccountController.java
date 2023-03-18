package com.telran.bank.controller;

import com.telran.bank.dto.AccountRequestDTO;
import com.telran.bank.dto.AccountResponseDTO;
import com.telran.bank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Tag(name = "Controller for managing accounts")
public class AccountController {

    private final AccountService accountService;

    private static final String ID = "/{id}";

    @PostMapping()
    @ResponseStatus(CREATED)
    @ApiResponse(responseCode = "201", description = "Successfully created an account!", content = {
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountResponseDTO.class))
    })
    @Operation(summary = "Creating a new account", description = "Requires email, firstname, lastname, country and city to create an account. Returns the created account.")
    public AccountResponseDTO createAccount(@RequestBody @Valid AccountRequestDTO accountRequestDTO) {
        return accountService.saveAccount(accountRequestDTO);
    }

    @GetMapping()
    @ResponseStatus(OK)
    @ApiResponse(responseCode = "200", description = "Successfully returned list of accounts", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AccountResponseDTO.class)))
    })
    @Operation(summary = "Request for all accounts", description = "Getting all accounts with given date, city and a way to sort. Getting all accounts if no params entered")
    public List<AccountResponseDTO> getAllAccounts(@RequestParam(required = false) String date,
                                                   @RequestParam(required = false) List<String> city,
                                                   @RequestParam(required = false) String sort) {
        return accountService.getAllAccounts(date, city, sort);
    }

    @GetMapping(ID)
    @ResponseStatus(OK)
    @ApiResponse(responseCode = "200", description = "Successfully returned the account!", content = {
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountResponseDTO.class))
    })
    @Operation(summary = "Request for a specific account ", description = "Getting an existing account by id")
    public AccountResponseDTO getAccount(@PathVariable String id) {
        return accountService.getAccount(id);
    }

    @PatchMapping(ID)
    @ResponseStatus(OK)
    @ApiResponse(responseCode = "200", description = "Successfully updated the account", content = {
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountResponseDTO.class))
    })
    @Operation(summary = "Updating an existing account by id", description = "Requires email, firstname, lastname, country or/and city to update an account. Returns the updated account.")
    public AccountResponseDTO patchAccount(@PathVariable String id,
                                           @RequestBody AccountRequestDTO accountRequestDTO) {
        return accountService.editAccount(id, accountRequestDTO);
    }
}