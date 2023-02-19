package com.telran.bank.service;

import com.telran.bank.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    TransactionDTO getTransaction(Long id);

    List<TransactionDTO> getAllTransactions(String date, String type, String sort);
}