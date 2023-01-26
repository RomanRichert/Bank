package com.telran.bank.repository;

import com.telran.bank.entity.Transaction;
import com.telran.bank.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.dateTime = ?1")
    List<Transaction> findByDateTime(LocalDate dateTime);

    @Query("select t from Transaction t where t.type = ?1")
    List<Transaction> findByType(TransactionType type);

    @Query("select t from Transaction t where t.type = ?1 and t.dateTime = ?2")
    List<Transaction> findByTypeAndDateTime(TransactionType type, LocalDate dateTime);

    @Query("select t from Transaction t order by t.dateTime DESC")
    List<Transaction> findAllOrderByDateTimeDesc();

    @Query("select t from Transaction t where t.dateTime = ?1 order by t.dateTime DESC")
    List<Transaction> findByDateTimeOrderByDateTimeDesc(LocalDate dateTime);

    @Query("select t from Transaction t where t.type = ?1 order by t.dateTime DESC")
    List<Transaction> findByTypeOrderByDateTimeDesc(TransactionType type);

    @Query("select t from Transaction t where t.type = ?1 and t.dateTime = ?2 order by t.dateTime DESC")
    List<Transaction> findByTypeAndDateTimeOrderByDateTimeDesc(TransactionType type, LocalDate dateTime);

    @Query("select t from Transaction t order by t.dateTime")
    List<Transaction> findAllOrderByDateTimeAsc();

    @Query("select t from Transaction t where t.dateTime = ?1 order by t.dateTime")
    List<Transaction> findByDateTimeOrderByDateTimeAsc(LocalDate dateTime);

    @Query("select t from Transaction t where t.type = ?1 order by t.dateTime")
    List<Transaction> findByTypeOrderByDateTimeAsc(TransactionType type);

    @Query("select t from Transaction t where t.type = ?1 and t.dateTime = ?2 order by t.dateTime")
    List<Transaction> findByTypeAndDateTimeOrderByDateTimeAsc(TransactionType type, LocalDate dateTime);
}