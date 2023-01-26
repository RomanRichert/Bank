package com.telran.bank.service.util;

import com.telran.bank.entity.Account;

import java.util.List;

public interface AccountService {
    Account saveAccount(Account account);
    Account editAccount(Long id, Account account);
    Account getAccount(Long id);
    List<Account> getAllAccounts(String date, List<String> cities, String sort);
}
