package com.telran.bank.repository;

import com.telran.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByCityIn(Collection<String> cities);

    List<Account> findByCityInAndCreationDate(Collection<String> cities, LocalDate creationDate);

    List<Account> findByCityInOrderByCreationDateDesc(Collection<String> cities);

    List<Account> findByCityInAndCreationDateOrderByCreationDateDesc(Collection<String> cities, LocalDate creationDate);

    List<Account> findByCityInOrderByCreationDateAsc(Collection<String> cities);

    List<Account> findByCityInAndCreationDateOrderByCreationDateAsc(Collection<String> cities, LocalDate creationDate);

    @Query("select a from Account a order by a.creationDate DESC")
    List<Account> findAllOrderedDesc();

    @Query("select a from Account a order by a.creationDate")
    List<Account> findAllOrderedAsc();

    List<Account> findByCreationDate(LocalDate creationDate);

    List<Account> findByCreationDateOrderByCreationDateDesc(LocalDate creationDate);

    List<Account> findByCreationDateOrderByCreationDateAsc(LocalDate creationDate);
}