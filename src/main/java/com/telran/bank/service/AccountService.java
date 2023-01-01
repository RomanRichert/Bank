package com.telran.bank.service;

import java.util.Date;
import com.telran.bank.Entity.Account;
import com.telran.bank.Exception.BankAccountNotFoundException;
import com.telran.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    public List<Account> getAllAccounts(@RequestParam(required = false) Date date,
                                        @RequestParam(required = false) List<String> city,
                                        @RequestParam(required = false) String sort) {
        if(sort != null && !sort.isEmpty()){
            if(sort.equalsIgnoreCase("creationDate")){
                if(city != null && !city.isEmpty() && date != null){
                    //return all accounts with given CITY and DATE ordered ASCENDING by DATE
                    return accountRepository.findByCreationDateAndCityOrderByCreationDateAsc(date, city);
                } else if(city != null && !city.isEmpty()){
                    //return all accounts with given CITY ordered ASCENDING by DATE
                    return accountRepository.findByCityOrderByCreationDateAsc(city);
                } else if(date != null){
                    //return all accounts with given DATE ordered ASCENDING by DATE
                    return accountRepository.findByCreationDateOrderByCreationDateAsc(date);
                    //return all accounts ordered ASCENDING by DATE
                } else return accountRepository.findByOrderByCreationDateAsc();
            } else if(sort.equalsIgnoreCase("-creationDate")) {
                if(city != null && !city.isEmpty() && date != null){
                    //return all accounts with given CITY and DATE ordered DESCENDING by DATE
                    return accountRepository.findByCreationDateAndCityOrderByCreationDateDesc(date, city);
                } else if(city != null && !city.isEmpty()){
                    //return all accounts with given CITY ordered DESCENDING by DATE
                    return accountRepository.findByCityOrderByCreationDateDesc(city);
                } else if(date != null){
                    //return all accounts with given DATE ordered DESCENDING by DATE
                    return accountRepository.findByCreationDateOrderByCreationDateDesc(date);
                    //return all accounts ordered DESCENDING by DATE
                } else return accountRepository.findByOrderByCreationDateDesc();
            }
        } else if(city != null && !city.isEmpty() && date != null){
            //return all accounts with given CITY and DATE
            return accountRepository.findByCreationDateAndCity(date, city);
        } else if(city != null && !city.isEmpty()){
            //return all accounts with given CITY
            return accountRepository.findByCity(city);
        } else if(date != null){
            //return all accounts with given DATE
            return accountRepository.findByCreationDate(date);
        }

        //return all accounts
        return accountRepository.findAll();
    }
}
