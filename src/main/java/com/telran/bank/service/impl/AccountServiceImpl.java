package com.telran.bank.service.impl;

import com.telran.bank.dto.AccountRequestDTO;
import com.telran.bank.dto.AccountResponseDTO;
import com.telran.bank.entity.Account;
import com.telran.bank.entity.Transaction;
import com.telran.bank.exception.BadRequestException;
import com.telran.bank.exception.BankAccountNotFoundException;
import com.telran.bank.exception.enums.messages.ErrorMessage;
import com.telran.bank.exception.NotEnoughMoneyException;
import com.telran.bank.mapper.AccountMapper;
import com.telran.bank.repository.AccountRepository;
import com.telran.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;

    private final AccountRepository accountRepository;

    private final TransactionServiceImpl transactionServiceImpl;

    @Override
    public AccountResponseDTO saveAccount(AccountRequestDTO accountRequestDTO) {
        return accountMapper.toResponseDTO(accountRepository.save(accountMapper.toEntity(accountRequestDTO)));
    }

    @Override
    public AccountResponseDTO editAccount(String id, AccountRequestDTO accountRequestDTO) {
        return applyChangesToAccount(id, accountRequestDTO);
    }

    @Override
    public AccountResponseDTO getAccount(String id) {
        Account account = accountRepository.findById(id);
        checkAccount(account, id, null);

        return accountMapper.toResponseDTO(account);
    }

    @Override
    public List<AccountResponseDTO> getAllAccounts(String date, List<String> cities, String sort) {
        return accountMapper.accountsToAccountResponseDTOs(getAccountsWithParameters(date, cities, sort));
    }

    @Override
    public void putTransaction(String fromId,
                               String toId,
                               Double amount) {
        if (amount == 0) throw new BadRequestException(ErrorMessage.AMOUNT_IS_0.getMessage());

        Transaction transaction = transactionServiceImpl.createTransaction(fromId, toId, amount);

        Account toAccount = accountRepository.findById(toId);
        checkAccount(toAccount, toId, amount);

        if (!fromId.equals(toId)) {
            Account fromAccount = accountRepository.findById(fromId);
            checkAccount(fromAccount, fromId, amount);

            fromAccount.addTransaction(transaction);
            fromAccount.setAmountOfMoney(fromAccount.getAmountOfMoney().add(BigDecimal.valueOf(-amount)));
            accountRepository.updateAmountOfMoneyById(fromAccount.getAmountOfMoney(), fromId);
        }

        toAccount.addTransaction(transaction);
        toAccount.setAmountOfMoney(toAccount.getAmountOfMoney().add(BigDecimal.valueOf(amount)));
        accountRepository.updateAmountOfMoneyById(toAccount.getAmountOfMoney(), toId);
    }

    private void checkAccount(Account account, String id, Double amount){
        if (account == null) throw new BankAccountNotFoundException(id);
        if (amount != null && amount > account.getAmountOfMoney().doubleValue()) throw new NotEnoughMoneyException(id);
    }

    private AccountResponseDTO applyChangesToAccount(String id, @NotNull AccountRequestDTO account) {
        Account patchedAccount = accountRepository.findById(id);
        checkAccount(patchedAccount, id, null);

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


        return accountMapper.toResponseDTO(accountRepository.updateAccountById(
                patchedAccount.getEmail(),
                patchedAccount.getFirstName(),
                patchedAccount.getLastName(),
                patchedAccount.getCountry(),
                patchedAccount.getCity(),
                id)
        );
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
            return accountRepository.findByCityInAndCreationDate(cities, LocalDate.parse(date));
        } else if (cityIsNotNullOrEmpty) {
            //return all accounts with given CITY
            return accountRepository.findByCityIn(cities);
        } else if (dateIsNotNullOrEmpty) {
            //return all accounts with given DATE
            return accountRepository.findByCreationDate(LocalDate.parse(date));
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
            return accountRepository.findByCityInAndCreationDateOrderByCreationDateDesc(cities, LocalDate.parse(date));
        } else if (cityIsNotNullOrEmpty) {
            //return all accounts with given CITY ordered DESCENDING by DATE
            return accountRepository.findByCityInOrderByCreationDateDesc(cities);
        } else if (dateIsNotNullOrEmpty) {
            //return all accounts with given DATE ordered DESCENDING by DATE
            return accountRepository.findByCreationDateOrderByCreationDateDesc(LocalDate.parse(date));
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
            return accountRepository.findByCityInAndCreationDateOrderByCreationDateAsc(cities, LocalDate.parse(date));
        } else if (cityIsNotNullOrEmpty) {
            //return all accounts with given CITY ordered ASCENDING by DATE
            return accountRepository.findByCityInOrderByCreationDateAsc(cities);
        } else if (dateIsNotNullOrEmpty) {
            //return all accounts with given DATE ordered ASCENDING by DATE
            return accountRepository.findByCreationDateOrderByCreationDateAsc(LocalDate.parse(date));
            //return all accounts ordered ASCENDING by DATE
        } else return accountRepository.findAllOrderedAsc();
    }
}