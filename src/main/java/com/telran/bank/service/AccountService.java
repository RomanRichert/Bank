package com.telran.bank.service;

import com.telran.bank.dto.AccountDTO;

import java.util.List;

public interface AccountService {

    AccountDTO saveAccount(AccountDTO accountDTO);

    AccountDTO editAccount(String id, AccountDTO accountDTO);

    AccountDTO getAccount(String id);

    List<AccountDTO> getAllAccounts(String date, List<String> cities, String sort);
}