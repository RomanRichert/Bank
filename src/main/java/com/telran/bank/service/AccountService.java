package com.telran.bank.service;

import java.util.Date;
import com.telran.bank.Entity.Account;
import com.telran.bank.Entity.Transaction;
import com.telran.bank.Exception.BankAccountNotFoundException;
import com.telran.bank.Exception.NotEnoughMoneyException;
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
    public List<Account> getAllAccounts(@RequestParam(required = false) String date,
                                        @RequestParam(required = false) List<String> city,
                                        @RequestParam(required = false) String sort) {
        boolean dateIsNotNullOrEmpty = date != null && !date.isEmpty();
        boolean cityIsNotNullOrEmpty = city != null && !city.isEmpty();
        boolean dateAndCityAreNotNullOrEmpty = dateIsNotNullOrEmpty && cityIsNotNullOrEmpty;

        if(sort != null && !sort.isEmpty()){
            if(sort.equalsIgnoreCase("creationDate")){
                if(dateAndCityAreNotNullOrEmpty){
                    //return all accounts with given CITY and DATE ordered ASCENDING by DATE
                    return accountRepository.findByCreationDateAndCityOrderByCreationDateAsc(new Date(date), city);
                } else if(cityIsNotNullOrEmpty){
                    //return all accounts with given CITY ordered ASCENDING by DATE
                    return accountRepository.findByCityOrderByCreationDateAsc(city);
                } else if(dateIsNotNullOrEmpty){
                    //return all accounts with given DATE ordered ASCENDING by DATE
                    return accountRepository.findByCreationDateOrderByCreationDateAsc(new Date(date));
                    //return all accounts ordered ASCENDING by DATE
                } else return accountRepository.findAllOrderByCreationDateAsc();
            } else if(sort.equalsIgnoreCase("-creationDate")) {
                if(dateAndCityAreNotNullOrEmpty){
                    //return all accounts with given CITY and DATE ordered DESCENDING by DATE
                    return accountRepository.findByCreationDateAndCityOrderByCreationDateDesc(new Date(date), city);
                } else if(cityIsNotNullOrEmpty){
                    //return all accounts with given CITY ordered DESCENDING by DATE
                    return accountRepository.findByCityOrderByCreationDateDesc(city);
                } else if(dateIsNotNullOrEmpty){
                    //return all accounts with given DATE ordered DESCENDING by DATE
                    return accountRepository.findByCreationDateOrderByCreationDateDesc(new Date(date));
                    //return all accounts ordered DESCENDING by DATE
                } else return accountRepository.findAllOrderByCreationDateDesc();
            }
        } else if(dateAndCityAreNotNullOrEmpty){
            //return all accounts with given CITY and DATE
            return accountRepository.findByCreationDateAndCity(new Date(date), city);
        } else if(cityIsNotNullOrEmpty){
            //return all accounts with given CITY
            return accountRepository.findByCity(city);
        } else if(dateIsNotNullOrEmpty){
            //return all accounts with given DATE
            return accountRepository.findByCreationDate(new Date(date));
        }

        //return all accounts
        return accountRepository.findAll();
    }
    public void putTransaction(Long fromId,
                                      Long toId,
                                      Double moneyAmount,
                               Transaction transaction){

        Account fromAccount = accountRepository.findById(fromId).orElseThrow(() -> new BankAccountNotFoundException("id = " + fromId));
        Account toAccount = accountRepository.findById(toId).orElseThrow(() -> new BankAccountNotFoundException("id = " + toId));

        if(moneyAmount > fromAccount.getAmountOfMoney().doubleValue()) throw new NotEnoughMoneyException("Not enough money on this account: "+fromId);

        fromAccount.addTransactions(transaction);
        fromAccount.setAmountOfMoney(-moneyAmount);
        editAccount(fromId, fromAccount);

        toAccount.addTransactions(transaction);
        toAccount.setAmountOfMoney(moneyAmount);
        editAccount(toId, toAccount);
    }
}
