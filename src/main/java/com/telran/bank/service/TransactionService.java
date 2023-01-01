package com.telran.bank.service;

import com.telran.bank.Entity.Transaction;
import com.telran.bank.Exception.TransactionNotFoundException;
import com.telran.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public Transaction getTransaction(Long id) throws TransactionNotFoundException {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("id = " + id));
    }
    public List<Transaction> getAllTransactions(@RequestParam(required = false) Date date,
                                        @RequestParam(required = false) String type,
                                        @RequestParam(required = false) String sort) {
        //some code needed

        return transactionRepository.findAll();
    }

}
