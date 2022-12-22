package com.telran.bank.service;

import com.telran.bank.Entity.Account;
import com.telran.bank.Exception.BankAccountNotFoundException;
import com.telran.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }
    public Account editAccount(Long id, Account account){
        Account patchedAccount = getAccount(id);

        if(account.getCity() != null && !account.getCity().isEmpty()) patchedAccount.setCity(account.getCity());
        if(account.getCountry() != null && !account.getCountry().isEmpty()) patchedAccount.setCountry(account.getCountry());
        if(account.getEmail() != null && !account.getEmail().isEmpty()) patchedAccount.setEmail(account.getEmail());
        if(account.getFirstName() != null && !account.getFirstName().isEmpty()) patchedAccount.setFirstName(account.getFirstName());
        if(account.getLastName() != null && !account.getLastName().isEmpty()) patchedAccount.setLastName(account.getLastName());

        return accountRepository.save(patchedAccount);
    }
    public Account getAccount(Long id) throws BankAccountNotFoundException {
         return accountRepository.findById(id)
                  .orElseThrow(() -> new BankAccountNotFoundException("id = " + id));
    }
    public List<Account> getAllAccounts(@RequestParam(required = false) String  date,
                                        @RequestParam(required = false) List<String> city,
                                        @RequestParam(required = false) String sort) {

        //some code needed

        return accountRepository.findAll();
    }
    public void deleteAccaunt(Long id){
        accountRepository.deleteById(id);
    }
}
