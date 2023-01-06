package com.telran.bank.repository;

import com.telran.bank.Entity.Transaction;
import com.telran.bank.Enum.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.dateTime = ?1")
    List<Transaction> findByDateTime(Date dateTime);
    @Query("select t from Transaction t where t.type = ?1")
    List<Transaction> findByType(TransactionType type);
    @Query("select t from Transaction t where t.type = ?1 and t.dateTime = ?2")
    List<Transaction> findByTypeAndDateTime(TransactionType type, Date dateTime);

    @Query("select t from Transaction t order by t.dateTime DESC")
    List<Transaction> findAllOrderByDateTimeDesc();
    @Query("select t from Transaction t where t.dateTime = ?1 order by t.dateTime DESC")
    List<Transaction> findByDateTimeOrderByDateTimeDesc(Date dateTime);
    @Query("select t from Transaction t where t.type = ?1 order by t.dateTime DESC")
    List<Transaction> findByTypeOrderByDateTimeDesc(TransactionType type);
    @Query("select t from Transaction t where t.type = ?1 and t.dateTime = ?2 order by t.dateTime DESC")
    List<Transaction> findByTypeAndDateTimeOrderByDateTimeDesc(TransactionType type, Date dateTime);
    @Query("select t from Transaction t order by t.dateTime")
    List<Transaction> findAllOrderByDateTimeAsc();
    @Query("select t from Transaction t where t.dateTime = ?1 order by t.dateTime")
    List<Transaction> findByDateTimeOrderByDateTimeAsc(Date dateTime);
    @Query("select t from Transaction t where t.type = ?1 order by t.dateTime")
    List<Transaction> findByTypeOrderByDateTimeAsc(TransactionType type);
    @Query("select t from Transaction t where t.type = ?1 and t.dateTime = ?2 order by t.dateTime")
    List<Transaction> findByTypeAndDateTimeOrderByDateTimeAsc(TransactionType type, Date dateTime);
}