package com.telran.bank.service.util;

import com.telran.bank.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);
    Transaction getTransaction(Long id);
    List<Transaction> getAllTransactions(String date, String type, String sort);
}
