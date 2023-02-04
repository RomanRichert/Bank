package com.telran.bank.repository;

import com.telran.bank.entity.Transaction;
import com.telran.bank.entity.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByDateTime(LocalDate dateTime);

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByTypeAndDateTime(TransactionType type, LocalDate dateTime);

    @Query("select t from Transaction t order by t.dateTime DESC")
    List<Transaction> findAllOrderedDesc();

    List<Transaction> findByDateTimeOrderByDateTimeDesc(LocalDate dateTime);

    List<Transaction> findByTypeOrderByDateTimeDesc(TransactionType type);

    List<Transaction> findByTypeAndDateTimeOrderByDateTimeDesc(TransactionType type, LocalDate dateTime);

    @Query("select t from Transaction t order by t.dateTime")
    List<Transaction> findAllOrderedAsc();

    List<Transaction> findByDateTimeOrderByDateTimeAsc(LocalDate dateTime);

    List<Transaction> findByTypeOrderByDateTimeAsc(TransactionType type);

    List<Transaction> findByTypeAndDateTimeOrderByDateTimeAsc(TransactionType type, LocalDate dateTime);
}