package com.telran.bank.service;

import com.telran.bank.dto.TransactionDTO;
import com.telran.bank.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction saveTransaction(Transaction transaction);

    TransactionDTO getTransaction(Long id);

    List<TransactionDTO> getAllTransactions(String date, String type, String sort);
}