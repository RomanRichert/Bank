package com.telran.bank.service;

import com.telran.bank.dto.AccountDTO;
import com.telran.bank.entity.Account;

import java.util.List;

public interface AccountService {

    AccountDTO saveAccount(Account accountDTO);

    AccountDTO editAccount(Long id, Account accountDTO);

    AccountDTO getAccount(Long id);

    List<AccountDTO> getAllAccounts(String date, List<String> cities, String sort);
}