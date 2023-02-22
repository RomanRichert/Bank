package com.telran.bank.service.impl;

import com.telran.bank.dto.AccountRequestDTO;
import com.telran.bank.dto.AccountResponseDTO;
import com.telran.bank.entity.Account;
import com.telran.bank.entity.Transaction;
import com.telran.bank.mapper.AccountMapper;
import com.telran.bank.repository.AccountRepository;
import com.telran.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.telran.bank.service.util.RequestChecker.*;
import static java.time.LocalDate.parse;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    private final AccountRepository accountRepository;

    private final TransactionServiceImpl transactionServiceImpl;

    @Override
    @Transactional
    public AccountResponseDTO saveAccount(AccountRequestDTO accountRequestDTO) {
        return accountMapper.toResponseDTO(accountRepository.save(accountMapper.toEntity(accountRequestDTO)));
    }

    @Override
    @Transactional
    public AccountResponseDTO editAccount(String id, AccountRequestDTO accountRequestDTO) {
        return applyChangesToAccount(id, accountRequestDTO);
    }

    @Override
    public AccountResponseDTO getAccount(String id) {
        Account account = accountRepository.findById(id);
        checkAccount(account, id);

        return accountMapper.toResponseDTO(account);
    }

    @Override
    public List<AccountResponseDTO> getAllAccounts(String date, List<String> cities, String sort) {
        checkDate(date);
        return accountMapper.accountsToAccountResponseDTOs(getAccountsWithParameters(date, cities, sort));
    }

    @Override
    @Transactional
    public void putTransaction(String fromId,
                               String toId,
                               Double amount) {
        Account fromAccount = accountRepository.findById(fromId);
        Account toAccount = accountRepository.findById(toId);
        checkTransactionPossibility(fromId, toId, amount, fromAccount, toAccount);

        Transaction transaction = transactionServiceImpl.createTransaction(fromId, toId, amount);

        if (!fromId.equals(toId)) {
            fromAccount.addTransaction(transaction);
            fromAccount.setAmountOfMoney(fromAccount.getAmountOfMoney().add(BigDecimal.valueOf(-amount)));
            accountRepository.updateAmountOfMoneyById(fromAccount.getAmountOfMoney(), fromId);
        }

        toAccount.addTransaction(transaction);
        toAccount.setAmountOfMoney(toAccount.getAmountOfMoney().add(BigDecimal.valueOf(amount)));
        accountRepository.updateAmountOfMoneyById(toAccount.getAmountOfMoney(), toId);
    }

    private AccountResponseDTO applyChangesToAccount(String id, AccountRequestDTO account) {
        Account patchedAccount = accountRepository.findById(id);
        checkAccount(patchedAccount, id);

        if (account.getCity() != null && !account.getCity().isEmpty())
            patchedAccount.setCity(account.getCity());

        if (account.getCountry() != null && !account.getCountry().isEmpty())
            patchedAccount.setCountry(account.getCountry());

        if (account.getEmail() != null && !account.getEmail().isEmpty())
            patchedAccount.setEmail(account.getEmail());

        if (account.getFirstName() != null && !account.getFirstName().isEmpty())
            patchedAccount.setFirstName(account.getFirstName());

        if (account.getLastName() != null && !account.getLastName().isEmpty())
            patchedAccount.setLastName(account.getLastName());

        accountRepository.updateAccountById(
                patchedAccount.getEmail(),
                patchedAccount.getFirstName(),
                patchedAccount.getLastName(),
                patchedAccount.getCountry(),
                patchedAccount.getCity(),
                id
        );

        return getAccount(id);
    }

    private List<Account> getAccountsWithParameters(String date, List<String> cities, String sort) {
        boolean dateIsNotNullOrEmpty = date != null && !date.isBlank();
        boolean cityIsNotNullOrEmpty = cities != null && !cities.isEmpty();
        boolean dateAndCityAreNotNullOrEmpty = dateIsNotNullOrEmpty && cityIsNotNullOrEmpty;

        if (sort != null && !sort.isBlank()) {
            if (sort.equalsIgnoreCase("creationDate")) {
                return returnAccountsOrderedByDateAsc(date, cities, dateIsNotNullOrEmpty, cityIsNotNullOrEmpty, dateAndCityAreNotNullOrEmpty);

            } else if (sort.equalsIgnoreCase("-creationDate")) {
                return returnAccountsOrderedByDateDesc(date, cities, dateIsNotNullOrEmpty, cityIsNotNullOrEmpty, dateAndCityAreNotNullOrEmpty);

            } else
                return returnAccountsWithoutOrder(date, cities, dateIsNotNullOrEmpty, cityIsNotNullOrEmpty, dateAndCityAreNotNullOrEmpty);

        } else
            return returnAccountsWithoutOrder(date, cities, dateIsNotNullOrEmpty, cityIsNotNullOrEmpty, dateAndCityAreNotNullOrEmpty);
    }

    private List<Account> returnAccountsWithoutOrder(String date,
                                                     List<String> cities,
                                                     boolean dateIsNotNullOrEmpty,
                                                     boolean cityIsNotNullOrEmpty,
                                                     boolean dateAndCityAreNotNullOrEmpty) {
        if (dateAndCityAreNotNullOrEmpty) {
            //return all accounts with given CITY and DATE
            return accountRepository.findByCityInAndCreationDate(cities, parse(date));
        } else if (cityIsNotNullOrEmpty) {
            //return all accounts with given CITY
            return accountRepository.findByCityIn(cities);
        } else if (dateIsNotNullOrEmpty) {
            //return all accounts with given DATE
            return accountRepository.findByCreationDate(parse(date));
        } else
            //return all accounts
            return accountRepository.findAll();
    }

    private List<Account> returnAccountsOrderedByDateDesc(String date,
                                                          List<String> cities,
                                                          boolean dateIsNotNullOrEmpty,
                                                          boolean cityIsNotNullOrEmpty,
                                                          boolean dateAndCityAreNotNullOrEmpty) {
        if (dateAndCityAreNotNullOrEmpty) {
            //return all accounts with given CITY and DATE ordered DESCENDING by DATE
            return accountRepository.findByCityInAndCreationDateOrderByCreationDateDesc(cities, parse(date));
        } else if (cityIsNotNullOrEmpty) {
            //return all accounts with given CITY ordered DESCENDING by DATE
            return accountRepository.findByCityInOrderByCreationDateDesc(cities);
        } else if (dateIsNotNullOrEmpty) {
            //return all accounts with given DATE ordered DESCENDING by DATE
            return accountRepository.findByCreationDateOrderByCreationDateDesc(parse(date));
            //return all accounts ordered DESCENDING by DATE
        } else return accountRepository.findAllOrderedDesc();
    }

    private List<Account> returnAccountsOrderedByDateAsc(String date,
                                                         List<String> cities,
                                                         boolean dateIsNotNullOrEmpty,
                                                         boolean cityIsNotNullOrEmpty,
                                                         boolean dateAndCityAreNotNullOrEmpty) {
        if (dateAndCityAreNotNullOrEmpty) {
            //return all accounts with given CITY and DATE ordered ASCENDING by DATE
            return accountRepository.findByCityInAndCreationDateOrderByCreationDateAsc(cities, parse(date));
        } else if (cityIsNotNullOrEmpty) {
            //return all accounts with given CITY ordered ASCENDING by DATE
            return accountRepository.findByCityInOrderByCreationDateAsc(cities);
        } else if (dateIsNotNullOrEmpty) {
            //return all accounts with given DATE ordered ASCENDING by DATE
            return accountRepository.findByCreationDateOrderByCreationDateAsc(parse(date));
            //return all accounts ordered ASCENDING by DATE
        } else return accountRepository.findAllOrderedAsc();
    }
}