package com.telran.bank.service.impl;

import com.telran.bank.dto.TransactionDTO;
import com.telran.bank.entity.Transaction;
import com.telran.bank.entity.enums.TransactionType;
import com.telran.bank.exception.BadRequestException;
import com.telran.bank.exception.TransactionNotFoundException;
import com.telran.bank.mapper.TransactionMapper;
import com.telran.bank.repository.TransactionRepository;
import com.telran.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static com.telran.bank.entity.enums.TransactionType.*;
import static com.telran.bank.exception.enums.messages.ErrorMessage.TRANSFER_AMOUNT_IS_NEGATIVE;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final TransactionMapper transactionMapper;

    public Transaction createTransaction(String fromId, String toId, Double amount) {
        if (Objects.equals(fromId, toId)) {
            if (amount < 0) {
                return saveTransaction(new Transaction(ATM_WITHDRAW, fromId, toId, amount));
            } else {
                return saveTransaction(new Transaction(ATM_DEPOSIT, fromId, toId, amount));
            }
        } else if (amount < 0) {
            throw new BadRequestException(TRANSFER_AMOUNT_IS_NEGATIVE.getMessage());
        } else {
            return saveTransaction(new Transaction(MONEY_TRANSFER, fromId, toId, amount));
        }
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public TransactionDTO getTransaction(Long id) {

        return transactionMapper.toDTO(transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("id = " + id)));
    }

    @Override
    public List<TransactionDTO> getAllTransactions(String date, String type, String sort) {
        return transactionMapper.transactionsToTransactionDTOs(getTransactionsWithParameters(date, type, sort));
    }

    private List<Transaction> getTransactionsWithParameters(String date, String type, String sort) {
        boolean dateIsNotNullOrEmpty = date != null && !date.isBlank();
        boolean typeIsNotNullOrEmpty = type != null && !type.isEmpty();
        boolean dateAndTypeAreNotNullOrEmpty = typeIsNotNullOrEmpty && dateIsNotNullOrEmpty;

        if (sort != null && !sort.isBlank()) {
            if (sort.equalsIgnoreCase("dateTime")) {
                return returnTransactionsOrderedByDateAsc(type, date, dateIsNotNullOrEmpty, typeIsNotNullOrEmpty, dateAndTypeAreNotNullOrEmpty);

            } else if (sort.equalsIgnoreCase("-dateTime")) {
                return returnTransactionsOrderedByDateDesc(type, date, dateIsNotNullOrEmpty, typeIsNotNullOrEmpty, dateAndTypeAreNotNullOrEmpty);

            } else
                return returnTransactionsWithoutOrder(type, date, dateIsNotNullOrEmpty, typeIsNotNullOrEmpty, dateAndTypeAreNotNullOrEmpty);

        } else
            return returnTransactionsWithoutOrder(type, date, dateIsNotNullOrEmpty, typeIsNotNullOrEmpty, dateAndTypeAreNotNullOrEmpty);
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
        } else return transactionRepository.findAllOrderedDesc();
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
        } else return transactionRepository.findAllOrderedAsc();
    }
}