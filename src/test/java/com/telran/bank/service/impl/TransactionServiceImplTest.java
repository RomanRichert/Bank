package com.telran.bank.service.impl;

import com.telran.bank.entity.Transaction;
import com.telran.bank.entity.enums.TransactionType;
import com.telran.bank.exception.InvalidDateException;
import com.telran.bank.exception.InvalidTransactionTypeException;
import com.telran.bank.exception.TransactionNotFoundException;
import com.telran.bank.mapper.TransactionMapper;
import com.telran.bank.mapper.TransactionMapperImpl;
import com.telran.bank.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.telran.bank.util.EntityCreator.*;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.util.Assert.noNullElements;
import static org.springframework.util.Assert.notEmpty;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Spy
    private TransactionMapper transactionMapper = new TransactionMapperImpl();

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    @DisplayName("Testing Transaction creation")
    void createTransaction() {
        String fromID = ACCOUNT1.getId();
        String toId = ACCOUNT2.getId();
        Double amount1 = TRANSACTION_MONEY_TRANSFER.getAmount().doubleValue();
        Double amount2 = TRANSACTION_ATM_WITHDRAW.getAmount().doubleValue();
        Double amount3 = TRANSACTION_ATM_DEPOSIT.getAmount().doubleValue();

        when(transactionRepository.save(any(Transaction.class))).thenReturn(TRANSACTION_MONEY_TRANSFER);
        assertEquals(TRANSACTION_MONEY_TRANSFER, transactionService.createTransaction(fromID, toId, amount1), "Something wrong by money_transfer");

        when(transactionRepository.save(any(Transaction.class))).thenReturn(TRANSACTION_ATM_WITHDRAW);
        assertEquals(TRANSACTION_ATM_WITHDRAW, transactionService.createTransaction(fromID, fromID, amount2), "Something wrong by ATM_withdraw");

        when(transactionRepository.save(any(Transaction.class))).thenReturn(TRANSACTION_ATM_DEPOSIT);
        assertEquals(TRANSACTION_ATM_DEPOSIT, transactionService.createTransaction(toId, toId, amount3), "Something wrong by ATM_deposit");

        assertThrows(NullPointerException.class, () -> transactionService.createTransaction(null, null, null), "NullPointerException should have been thrown");

        verify(transactionRepository, times(3)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Tests getting a Transaction by id")
    void getTransaction() {
        Long id = TRANSACTION_MONEY_TRANSFER.getId();

        when(transactionRepository.findById(id)).thenReturn(Optional.of(TRANSACTION_MONEY_TRANSFER));

        assertEquals(transactionMapper.toDTO(TRANSACTION_MONEY_TRANSFER), transactionService.getTransaction(id), "Something went wrong by getting of the Transaction");
        assertThrows(TransactionNotFoundException.class, () -> transactionService.getTransaction(1L), "com.telran.bank.exception.TransactionNotFoundException.class wasn't thrown");
        verify(transactionRepository).findById(id);
        verify(transactionRepository).findById(1L);
    }

    @Test
    @DisplayName("Tests getting all Transactions with given parameters")
    void getAllTransactions() {
        String date = "2023-02-18";
        String type = TRANSACTION_MONEY_TRANSFER.getType().toString();
        String sort = "-dateTime";
        List<Transaction> list = List.of(TRANSACTION_MONEY_TRANSFER);

        when(transactionRepository.findByTypeAndCreationDateOrderByCreationDateDesc(TransactionType.valueOf(type), parse(date, ofPattern("yyyy-MM-dd")))).thenReturn(list);

        assertAll(
                () -> assertEquals(transactionMapper.transactionsToTransactionDTOs(list), transactionService.getAllTransactions(date, type, sort), "There should be the TRANSACTION_MONEY_TRANSFER in the list"),
                () -> assertThrows(InvalidDateException.class, () -> transactionService.getAllTransactions("date", type, sort), "InvalidDateException should be thrown"),
                () -> assertThrows(InvalidTransactionTypeException.class, () -> transactionService.getAllTransactions(date, "type", sort), "InvalidTransactionTypeException should be thrown"),
                () -> noNullElements(transactionService.getAllTransactions(date, type, sort), "There are nulls in the list"),
                () -> notEmpty(transactionService.getAllTransactions(date, type, sort) , "The list is empty"),
                () -> assertNotNull(transactionService.getAllTransactions(date, type, sort) ,"The list is null"),
                () -> verify(transactionRepository, times(4)).findByTypeAndCreationDateOrderByCreationDateDesc(TransactionType.valueOf(type), parse(date, ofPattern("yyyy-MM-dd")))
        );
    }
}