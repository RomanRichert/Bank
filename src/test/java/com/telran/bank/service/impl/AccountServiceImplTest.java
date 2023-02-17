package com.telran.bank.service.impl;

import com.telran.bank.dto.AccountRequestDTO;
import com.telran.bank.dto.AccountResponseDTO;
import com.telran.bank.entity.Account;
import com.telran.bank.exception.EntityNotFoundException;
import com.telran.bank.mapper.AccountMapper;
import com.telran.bank.mapper.AccountMapperImpl;
import com.telran.bank.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolationException;

import java.util.List;

import static com.telran.bank.util.DtoCreator.*;
import static com.telran.bank.util.EntityCreator.ACCOUNT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Spy
    private AccountMapper accountMapper = new AccountMapperImpl();

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionServiceImpl transactionService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("Tests saving account | Normal case")
    void saveAccountNormalCase() {
        Account account = ACCOUNT;
        AccountRequestDTO accountRequestDTO = getAccountRequestDTO();
        AccountResponseDTO accountResponseDTO = getAccountResponseDTO();

        when(accountMapper.toEntity(accountRequestDTO)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toResponseDTO(account)).thenReturn(accountResponseDTO);

        assertEquals(accountResponseDTO, accountService.saveAccount(accountRequestDTO));
        verify(accountRepository).save(account);
    }

    @Test
    @DisplayName("Tests saving account | ConstraintViolationException-throwing case")
    void saveAccountNULLCase() {
        AccountRequestDTO accountRequestDTO = getAccountRequestDTOWithNulls();

        when(accountMapper.toEntity(null)).thenReturn(null);
        when(accountMapper.toEntity(accountRequestDTO)).thenThrow(ConstraintViolationException.class);
        when(accountRepository.save(null)).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> accountService.saveAccount(null));
        assertThrows(ConstraintViolationException.class, () -> accountService.saveAccount(accountRequestDTO));
        verify(accountRepository).save(null);
    }

    @Test
    @DisplayName("Tests getting and editing an account with DTO")
    void editAccount() {
        String id = ACCOUNT.getId();
        AccountRequestDTO accountRequestDTO = getAccountRequestDTO();
        AccountResponseDTO accountResponseDTO = getAccountResponseDTO();

        when(accountRepository.findById(id)).thenReturn(ACCOUNT);

        assertEquals(accountResponseDTO, accountService.editAccount(id, accountRequestDTO));
        verify(accountRepository, times(2)).findById(id);
    }

    @Test
    @DisplayName("Tests getting and editing an account with DTO | Wrong id - case")
    void editAccountWrongIdCase() {
        String id = "anyString()";
        AccountRequestDTO accountRequestDTO = getAccountRequestDTO();

        when(accountRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> accountService.editAccount(id, accountRequestDTO));
        verify(accountRepository).findById(id);
    }

    @Test
    @DisplayName("Tests getting and editing an account with DTO with some NULL-fields")
    void editAccountWithDtoWithNulls() {
        String id = ACCOUNT.getId();
        AccountRequestDTO accountRequestDTO = getAccountRequestDTOWithNulls();
        AccountResponseDTO accountResponseDTO = getPatchedWithNullsAccountResponseDTO();

        when(accountRepository.findById(id)).thenReturn(ACCOUNT);

        assertEquals(accountResponseDTO, accountService.editAccount(id, accountRequestDTO));
        verify(accountRepository, times(2)).findById(id);
    }

    @Test
    void getAllAccounts() {
        List<String> cities = List.of("City");
        List<Account> list = List.of(ACCOUNT);
        List<AccountResponseDTO> accountResponseDTOs = List.of(getAccountResponseDTO());

        when(accountMapper.accountsToAccountResponseDTOs(list)).thenReturn(accountResponseDTOs);

        Assert.noNullElements(accountService.getAllAccounts("2023-02-17", cities, "-dateTime"), "");
    }

    @Test
    void putTransaction() {
    }
}