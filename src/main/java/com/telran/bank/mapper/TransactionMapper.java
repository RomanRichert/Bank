package com.telran.bank.mapper;

import com.telran.bank.dto.TransactionDTO;
import com.telran.bank.entity.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDTO toDTO(Transaction transaction);

    List<TransactionDTO> transactionsToTransactionDTOs(List<Transaction> transactions);

    static String getId(Transaction transaction){
        return transaction.getId().toString();
    }
}