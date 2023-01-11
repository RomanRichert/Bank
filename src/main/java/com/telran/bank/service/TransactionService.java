package com.telran.bank.service;

import com.telran.bank.Entity.Transaction;
import com.telran.bank.Enum.TransactionType;
import com.telran.bank.Exception.TransactionNotFoundException;
import com.telran.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public List<Transaction> getAllTransactions(@RequestParam(required = false) String date,
                                                @RequestParam(required = false) String type,
                                                @RequestParam(required = false) String sort) throws ParseException {
        boolean dateIsNotNullOrEmpty = date != null && !date.isBlank();
        boolean typeIsNotNullOrEmpty = type != null && !type.isEmpty();
        boolean dateAndTypeAreNotNullOrEmpty = typeIsNotNullOrEmpty && dateIsNotNullOrEmpty;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if(sort != null && !sort.isBlank()){
            if(sort.equalsIgnoreCase("dateTime")){
                if(dateAndTypeAreNotNullOrEmpty){
                    //return all accounts with given TYPE and DATE ordered ASCENDING by DATE
                    return transactionRepository.findByTypeAndDateTimeOrderByDateTimeAsc(TransactionType.valueOf(type), format.parse(date));
                } else if(typeIsNotNullOrEmpty){
                    //return all accounts with given TYPE ordered ASCENDING by DATE
                    return transactionRepository.findByTypeOrderByDateTimeAsc(TransactionType.valueOf(type));
                } else if(dateIsNotNullOrEmpty){
                    //return all accounts with given DATE ordered ASCENDING by DATE
                    return transactionRepository.findByDateTimeOrderByDateTimeAsc(format.parse(date));
                    //return all accounts ordered ASCENDING by DATE
                } else return transactionRepository.findAllOrderByDateTimeAsc();
            } else if(sort.equalsIgnoreCase("-dateTime")) {
                if(dateAndTypeAreNotNullOrEmpty){
                    //return all accounts with given TYPE and DATE ordered DESCENDING by DATE
                    return transactionRepository.findByTypeAndDateTimeOrderByDateTimeDesc(TransactionType.valueOf(type), format.parse(date));
                } else if(typeIsNotNullOrEmpty){
                    //return all accounts with given TYPE ordered DESCENDING by DATE
                    return transactionRepository.findByTypeOrderByDateTimeDesc(TransactionType.valueOf(type));
                } else if(dateIsNotNullOrEmpty){
                    //return all accounts with given DATE ordered DESCENDING by DATE
                    return transactionRepository.findByDateTimeOrderByDateTimeDesc(format.parse(date));
                    //return all accounts ordered DESCENDING by DATE
                } else return transactionRepository.findAllOrderByDateTimeDesc();
            }
        } else if(dateAndTypeAreNotNullOrEmpty){
            //return all accounts with given TYPE and DATE
            return transactionRepository.findByTypeAndDateTime(TransactionType.valueOf(type), format.parse(date));
        } else if(typeIsNotNullOrEmpty){
            //return all accounts with given TYPE
            return transactionRepository.findByType(TransactionType.valueOf(type));
        } else if(dateIsNotNullOrEmpty){
            //return all accounts with given DATE
            return transactionRepository.findByDateTime(format.parse(date));
        }

        return transactionRepository.findAll();
    }
}
