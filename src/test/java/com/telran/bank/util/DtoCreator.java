package com.telran.bank.util;

import com.telran.bank.dto.AccountRequestDTO;
import com.telran.bank.dto.AccountResponseDTO;
import com.telran.bank.dto.TransactionDTO;
import com.telran.bank.entity.Account;
import com.telran.bank.entity.Transaction;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

import static com.telran.bank.util.EntityCreator.ACCOUNT1;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

@UtilityClass
public class DtoCreator {

    public static AccountRequestDTO getAccountRequestDTO(){
        return new AccountRequestDTO(
                "email@email.com",
                "FirstName",
                "LastName",
                "Country",
                "City"
                );
    }

    public static AccountRequestDTO getAccountRequestDTOWithNulls(){
        return new AccountRequestDTO(
                null,
                null,
                null,
                "Germany",
                "Bonn"
                );
    }

    public static AccountRequestDTO getPatchingAccountRequestDTO(){
        return new AccountRequestDTO(
                "newemail@new.gmail",
                "New Firstname",
                "New Lastname",
                "New Country",
                "New City"
                );
    }

    public static AccountResponseDTO getAccountResponseDTO(){
        Account account = ACCOUNT1;
        return new AccountResponseDTO(
                account.getId(),
                account.getEmail(),
                account.getCreationDate().toString(),
                account.getFirstName(),
                account.getLastName(),
                account.getCountry(),
                account.getCity(),
                account.getAmountOfMoney().toString(),
                account.getTransactions().stream()
                        .map(Object::toString)
                        .collect(Collectors.toList())
        );
    }

    public static AccountResponseDTO getPatchedWithNullsAccountResponseDTO(){
        AccountResponseDTO actual = getAccountResponseDTO();
        AccountRequestDTO patch = getAccountRequestDTOWithNulls();
        return new AccountResponseDTO(
                actual.getId(),
                actual.getEmail(),
                actual.getCreationDate(),
                actual.getFirstName(),
                actual.getLastName(),
                patch.getCountry(),
                patch.getCity(),
                actual.getAmountOfMoney(),
                actual.getTransactions()
        );
    }

    public static AccountResponseDTO getPatchedAccountResponseDTO(){
        AccountResponseDTO actual = getAccountResponseDTO();
        AccountRequestDTO patch = getPatchingAccountRequestDTO();
        return new AccountResponseDTO(
                actual.getId(),
                patch.getEmail(),
                actual.getCreationDate(),
                patch.getFirstName(),
                patch.getLastName(),
                patch.getCountry(),
                patch.getCity(),
                actual.getAmountOfMoney(),
                actual.getTransactions()
        );
    }

    public static TransactionDTO getTransactionDTO(Transaction transaction){
        return new TransactionDTO(
                ISO_LOCAL_TIME.format( transaction.getCreationTime()),
                transaction.getCreationDate().toString(),
                transaction.getType().toString(),
                transaction.getAccountFrom(),
                transaction.getAccountTo(),
                transaction.getAmount().toString()
        );
    }
}