package com.telran.bank.service.impl;

import com.telran.bank.dto.AccountRequestDTO;
import com.telran.bank.dto.AccountResponseDTO;
import com.telran.bank.entity.Account;
import com.telran.bank.exception.EntityNotFoundException;
import com.telran.bank.exception.InvalidDateException;
import com.telran.bank.mapper.AccountMapper;
import com.telran.bank.mapper.AccountMapperImpl;
import com.telran.bank.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.telran.bank.util.DtoCreator.*;
import static com.telran.bank.util.EntityCreator.ACCOUNT1;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.util.Assert.noNullElements;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Spy
    private AccountMapper accountMapper = new AccountMapperImpl();

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("Tests saving account | Normal case")
    void saveAccountNormalCase() {
        Account account = ACCOUNT1;
        AccountRequestDTO accountRequestDTO = getAccountRequestDTO();
        AccountResponseDTO accountResponseDTO = getAccountResponseDTO();

        when(accountMapper.toEntity(accountRequestDTO)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toResponseDTO(account)).thenReturn(accountResponseDTO);

        assertEquals(accountResponseDTO, accountService.saveAccount(accountRequestDTO), "Something went wrong by saving of the account");
        verify(accountRepository).save(account);
    }

    @Test
    @DisplayName("Tests saving account | ConstraintViolationException-throwing case")
    void saveAccountNULLCase() {
        AccountRequestDTO accountRequestDTO = getAccountRequestDTOWithNulls();

        when(accountMapper.toEntity(null)).thenReturn(null);
        when(accountMapper.toEntity(accountRequestDTO)).thenThrow(ConstraintViolationException.class);
        when(accountRepository.save(null)).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> accountService.saveAccount(null), "ConstraintViolationException wasn't thrown | null-case");
        assertThrows(ConstraintViolationException.class, () -> accountService.saveAccount(accountRequestDTO), "ConstraintViolationException wasn't thrown | bad_AccountRequestDTO-case");
        verify(accountRepository).save(null);
    }

    @Test
    @DisplayName("Tests getting and editing an account with DTO")
    void editAccount() {
        String id = ACCOUNT1.getId();
        AccountRequestDTO accountRequestDTO = getPatchingAccountRequestDTO();
        AccountResponseDTO accountResponseDTO = getPatchedAccountResponseDTO();

        when(accountRepository.findById(id)).thenReturn(ACCOUNT1);

        assertEquals(accountResponseDTO, accountService.editAccount(id, accountRequestDTO), "Something by editing of the account went wrong");
        verify(accountRepository, times(2)).findById(id);
    }

    @Test
    @DisplayName("Tests getting and editing an account with DTO | Wrong id - case")
    void editAccountWrongIdCase() {
        String id = "anyString()";
        AccountRequestDTO accountRequestDTO = getAccountRequestDTO();

        when(accountRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> accountService.editAccount(id, accountRequestDTO), "com.telran.bank.exception.EntityNotFoundException wasn't thrown");
        verify(accountRepository).findById(id);
    }

    @Test
    @DisplayName("Tests getting and editing an account with DTO with some NULL-fields")
    void editAccountWithDtoWithNulls() {
        String id = ACCOUNT1.getId();
        AccountRequestDTO accountRequestDTO = getAccountRequestDTOWithNulls();
        AccountResponseDTO accountResponseDTO = getPatchedWithNullsAccountResponseDTO();

        when(accountRepository.findById(id)).thenReturn(ACCOUNT1);

        assertEquals(accountResponseDTO, accountService.editAccount(id, accountRequestDTO), "Something by editing of the account with DTO with some null-fields went wrong");
        assertThrows(NullPointerException.class, () -> accountService.editAccount(id, null), "Something by editing of the account with DTO with some null-fields went wrong");
        verify(accountRepository, times(3)).findById(id);
    }

    @Test
    @DisplayName("Tests getting all accounts with given parameters.")
    void getAllAccounts() {
        String date = "2023-02-17";
        List<String> cities = List.of("City");
        String sort = "-creationDate";
        List<Account> list = List.of(ACCOUNT1);
        List<AccountResponseDTO> accountResponseDTOs = List.of(getAccountResponseDTO());

        when(accountRepository.findByCityInAndCreationDateOrderByCreationDateDesc(any(), any())).thenReturn(list);

        assertAll(
                () -> noNullElements(accountService.getAllAccounts(date, cities, sort), "There are nulls in the list"),
                () -> notEmpty(accountService.getAllAccounts(date, cities, sort), "The list is empty"),
                () -> assertNotNull(accountService.getAllAccounts(date, cities, sort), "The list is null"),
                () -> assertEquals(accountResponseDTOs, accountService.getAllAccounts(date, cities, sort), "There should be the ACCOUNT in the list"),
                () -> assertThrows(InvalidDateException.class, () -> accountService.getAllAccounts("date", cities, sort), "InvalidDateException should be thrown"),
                () -> verify(accountRepository, times(4)).findByCityInAndCreationDateOrderByCreationDateDesc(any(), any())
        );
    }
}