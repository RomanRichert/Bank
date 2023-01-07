package com.telran.bank.service;

import com.telran.bank.Entity.Account;
import com.telran.bank.Entity.Transaction;
import com.telran.bank.Exception.BankAccountNotFoundException;
import com.telran.bank.Exception.NotEnoughMoneyException;
import com.telran.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }
    public Account editAccount(Long id, Account account) throws BankAccountNotFoundException{
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
                                        @RequestParam(required = false) List<String> cities,
                                        @RequestParam(required = false) String sort) {
        boolean dateIsNotNullOrEmpty = date != null && !date.isEmpty();
        boolean cityIsNotNullOrEmpty = cities != null && !cities.isEmpty();
        boolean dateAndCityAreNotNullOrEmpty = dateIsNotNullOrEmpty && cityIsNotNullOrEmpty;

        if(sort != null && !sort.isEmpty()){
            if(sort.equalsIgnoreCase("creationDate")){
                if(dateAndCityAreNotNullOrEmpty){
                    //return all accounts with given CITY and DATE ordered ASCENDING by DATE
                    return accountRepository.findByCityInAndCreationDateOrderByCreationDateAsc(cities, Date.valueOf(date));
                } else if(cityIsNotNullOrEmpty){
                    //return all accounts with given CITY ordered ASCENDING by DATE
                    return accountRepository.findByCityInOrderByCreationDateAsc(cities);
                } else if(dateIsNotNullOrEmpty){
                    //return all accounts with given DATE ordered ASCENDING by DATE
                    return accountRepository.findByCreationDateOrderByCreationDateAsc(Date.valueOf(date));
                    //return all accounts ordered ASCENDING by DATE
                } else return accountRepository.findAllOrderByCreationDateAsc();
            } else if(sort.equalsIgnoreCase("-creationDate")) {
                if(dateAndCityAreNotNullOrEmpty){
                    //return all accounts with given CITY and DATE ordered DESCENDING by DATE
                    return accountRepository.findByCityInAndCreationDateOrderByCreationDateDesc(cities, Date.valueOf(date));
                } else if(cityIsNotNullOrEmpty){
                    //return all accounts with given CITY ordered DESCENDING by DATE
                    return accountRepository.findByCityInOrderByCreationDateDesc(cities);
                } else if(dateIsNotNullOrEmpty){
                    //return all accounts with given DATE ordered DESCENDING by DATE
                    return accountRepository.findByCreationDateOrderByCreationDateDesc(Date.valueOf(date));
                    //return all accounts ordered DESCENDING by DATE
                } else return accountRepository.findAllOrderByCreationDateDesc();
            }
        } else if(dateAndCityAreNotNullOrEmpty){
            //return all accounts with given CITY and DATE
            return accountRepository.findByCityInAndCreationDate(cities, Date.valueOf(date));
        } else if(cityIsNotNullOrEmpty){
            //return all accounts with given CITY
            return accountRepository.findByCityIn(cities);
        } else if(dateIsNotNullOrEmpty){
            //return all accounts with given DATE
            return accountRepository.findByCreationDate(Date.valueOf(date));
        }

        //return all accounts
        return accountRepository.findAll();
    }
    public void putTransaction(Long fromId,
                                      Long toId,
                                      Double moneyAmount,
                               Transaction transaction) throws BankAccountNotFoundException, NotEnoughMoneyException{

        Account fromAccount = accountRepository.findById(fromId).orElseThrow(() -> new BankAccountNotFoundException("id = " + fromId));
        Account toAccount = accountRepository.findById(toId).orElseThrow(() -> new BankAccountNotFoundException("id = " + toId));

        if(moneyAmount > fromAccount.getAmountOfMoney().doubleValue()) throw new NotEnoughMoneyException("Not enough money on this account: "+fromId);

        if(!fromId.equals(toId)){
        fromAccount.addTransaction(transaction);
        fromAccount.setAmountOfMoney(-moneyAmount);
        editAccount(fromId, fromAccount);
        }

        toAccount.addTransaction(transaction);
        toAccount.setAmountOfMoney(moneyAmount);
        editAccount(toId, toAccount);
    }
}
