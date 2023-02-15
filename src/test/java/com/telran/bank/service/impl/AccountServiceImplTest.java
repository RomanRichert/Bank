package com.telran.bank.service.impl;

import com.telran.bank.dto.AccountRequestDTO;
import com.telran.bank.entity.Account;
import com.telran.bank.mapper.AccountMapper;
import com.telran.bank.mapper.AccountMapperImpl;
import com.telran.bank.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Spy
    private AccountMapper accountMapper = new AccountMapperImpl();

    @Mock
    private AccountRepository accountRepository;

    private final AccountRequestDTO accountRequestDTO = new AccountRequestDTO(
            "email@email.com",
            "Model",
            "Sample",
            "Germany",
            "Berlin"
            );

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void saveAccount() {
        Account account = accountMapper.toEntity(accountRequestDTO);

        when(accountRepository.save(account)).thenReturn(account);
        assertEquals(accountService.saveAccount(accountRequestDTO), accountMapper.toResponseDTO(account));
        verify(accountRepository).save(account);
    }

    @Test
    void editAccount() {

    }

    @Test
    void getAccount() {
    }

    @Test
    void getAllAccounts() {
    }

    @Test
    void putTransaction() {
    }
}