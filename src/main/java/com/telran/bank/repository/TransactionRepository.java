package com.telran.bank.repository;

import com.telran.bank.entity.Transaction;
import com.telran.bank.entity.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCreationDate(LocalDate creationDate);

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByTypeAndCreationDate(TransactionType type, LocalDate creationDate);

    @Query("select t from Transaction t order by t.creationDate DESC")
    List<Transaction> findAllOrderedDesc();

    List<Transaction> findByCreationDateOrderByCreationDateDesc(LocalDate creationDate);

    List<Transaction> findByTypeOrderByCreationDateDesc(TransactionType type);

    List<Transaction> findByTypeAndCreationDateOrderByCreationDateDesc(TransactionType type, LocalDate creationDate);

    @Query("select t from Transaction t order by t.creationDate")
    List<Transaction> findAllOrderedAsc();

    List<Transaction> findByCreationDateOrderByCreationDateAsc(LocalDate dateTime);

    List<Transaction> findByTypeOrderByCreationDateAsc(TransactionType type);

    List<Transaction> findByTypeAndCreationDateOrderByCreationDateAsc(TransactionType type, LocalDate dateTime);
}