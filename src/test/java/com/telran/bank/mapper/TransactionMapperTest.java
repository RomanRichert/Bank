package com.telran.bank.mapper;

import com.telran.bank.dto.TransactionDTO;
import com.telran.bank.entity.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.telran.bank.util.DtoCreator.getTransactionDTO;
import static com.telran.bank.util.EntityCreator.TRANSACTION_MONEY_TRANSFER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionMapperTest {

    TransactionMapper transactionMapper = new TransactionMapperImpl();

    @Test
    @DisplayName("Tests mapping of the Transaction to TransactionDTO")
    void toDTO() {
        assertEquals(getTransactionDTO(TRANSACTION_MONEY_TRANSFER), transactionMapper.toDTO(TRANSACTION_MONEY_TRANSFER), "Something went wrong by mapping Transaction to TransactionDTO");
    }

    @Test
    @DisplayName("Testing of mapping List<Transaction> to List<TransactionDTO> ")
    void transactionsToTransactionDTOs() {
        List<Transaction> transactions = List.of(TRANSACTION_MONEY_TRANSFER);
        List<TransactionDTO> dtos = List.of(getTransactionDTO(TRANSACTION_MONEY_TRANSFER));

        assertEquals(dtos, transactionMapper.transactionsToTransactionDTOs(transactions), "Something went wrong by mapping List<Transaction> to List<TransactionDTO>");
    }
}