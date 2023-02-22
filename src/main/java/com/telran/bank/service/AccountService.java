package com.telran.bank.service;

import com.telran.bank.dto.AccountRequestDTO;
import com.telran.bank.dto.AccountResponseDTO;

import java.util.List;

public interface AccountService {

    AccountResponseDTO saveAccount(AccountRequestDTO accountRequestDTO);

    AccountResponseDTO editAccount(String id, AccountRequestDTO accountRequestDTO);

    AccountResponseDTO getAccount(String id);

    List<AccountResponseDTO> getAllAccounts(String date, List<String> cities, String sort);

    void putTransaction(String from, String to, Double amount);
}