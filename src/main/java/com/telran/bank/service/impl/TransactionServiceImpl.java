package com.telran.bank.service.impl;

import com.telran.bank.entity.Transaction;
import com.telran.bank.enums.TransactionType;
import com.telran.bank.exception.TransactionNotFoundException;
import com.telran.bank.repository.TransactionRepository;
import com.telran.bank.service.util.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction getTransaction(Long id) throws TransactionNotFoundException {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("id = " + id));
    }

    public List<Transaction> getAllTransactions(String date, String type, String sort) {

        boolean dateIsNotNullOrEmpty = date != null && !date.isBlank();
        boolean typeIsNotNullOrEmpty = type != null && !type.isEmpty();
        boolean dateAndTypeAreNotNullOrEmpty = typeIsNotNullOrEmpty && dateIsNotNullOrEmpty;

        if (sort != null && !sort.isBlank()) {
            if (sort.equalsIgnoreCase("dateTime")) {
                return returnTransactionsOrderedByDateAsc(type, date, dateIsNotNullOrEmpty, typeIsNotNullOrEmpty, dateAndTypeAreNotNullOrEmpty);

            } else if (sort.equalsIgnoreCase("-dateTime")) {
                return returnTransactionsOrderedByDateDesc(type, date, dateIsNotNullOrEmpty, typeIsNotNullOrEmpty, dateAndTypeAreNotNullOrEmpty);

            } else return returnTransactionsWithoutOrder(type, date, dateIsNotNullOrEmpty, typeIsNotNullOrEmpty, dateAndTypeAreNotNullOrEmpty);

        } else return transactionRepository.findAll();
    }

    private List<Transaction> returnTransactionsWithoutOrder(String type,
                                                             String date,
                                                             boolean dateIsNotNullOrEmpty,
                                                             boolean typeIsNotNullOrEmpty,
                                                             boolean dateAndTypeAreNotNullOrEmpty) {
        if (dateAndTypeAreNotNullOrEmpty) {
            //return all transactions with given TYPE and DATE
            return transactionRepository.findByTypeAndDateTime(TransactionType.valueOf(type), LocalDate.parse(date, format));

        } else if (typeIsNotNullOrEmpty) {
            //return all transactions with given TYPE
            return transactionRepository.findByType(TransactionType.valueOf(type));

        } else if (dateIsNotNullOrEmpty) {
            //return all transactions with given DATE
            return transactionRepository.findByDateTime(LocalDate.parse(date, format));

            //return all transactions
        } else return transactionRepository.findAll();
    }

    private List<Transaction> returnTransactionsOrderedByDateDesc(String type,
                                                                  String date,
                                                                  boolean dateIsNotNullOrEmpty,
                                                                  boolean typeIsNotNullOrEmpty,
                                                                  boolean dateAndTypeAreNotNullOrEmpty) {
        if (dateAndTypeAreNotNullOrEmpty) {
            //return all transactions with given TYPE and DATE ordered DESCENDING by DATE
            return transactionRepository.findByTypeAndDateTimeOrderByDateTimeDesc(TransactionType.valueOf(type), LocalDate.parse(date, format));

        } else if (typeIsNotNullOrEmpty) {
            //return all transactions with given TYPE ordered DESCENDING by DATE
            return transactionRepository.findByTypeOrderByDateTimeDesc(TransactionType.valueOf(type));

        } else if (dateIsNotNullOrEmpty) {
            //return all transactions with given DATE ordered DESCENDING by DATE
            return transactionRepository.findByDateTimeOrderByDateTimeDesc(LocalDate.parse(date, format));

            //return all transactions ordered DESCENDING by DATE
        } else return transactionRepository.findAllOrderByDateTimeDesc();
    }

    private List<Transaction> returnTransactionsOrderedByDateAsc(String type,
                                                                 String date,
                                                                 boolean dateIsNotNullOrEmpty,
                                                                 boolean typeIsNotNullOrEmpty,
                                                                 boolean dateAndTypeAreNotNullOrEmpty) {
        if (dateAndTypeAreNotNullOrEmpty) {
            //return all transactions with given TYPE and DATE ordered ASCENDING by DATE
            return transactionRepository.findByTypeAndDateTimeOrderByDateTimeAsc(TransactionType.valueOf(type), LocalDate.parse(date, format));

        } else if (typeIsNotNullOrEmpty) {
            //return all transactions with given TYPE ordered ASCENDING by DATE
            return transactionRepository.findByTypeOrderByDateTimeAsc(TransactionType.valueOf(type));

        } else if (dateIsNotNullOrEmpty) {
            //return all transactions with given DATE ordered ASCENDING by DATE
            return transactionRepository.findByDateTimeOrderByDateTimeAsc(LocalDate.parse(date, format));

            //return all transactions ordered ASCENDING by DATE
        } else return transactionRepository.findAllOrderByDateTimeAsc();
    }
}
