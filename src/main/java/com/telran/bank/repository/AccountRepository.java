package com.telran.bank.repository;

import com.telran.bank.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;


import java.util.Collection;
import java.sql.Date;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select a from Account a where a.city in ?1")
    List<Account> findByCityIn(Collection<String> cities);
    @Query("select a from Account a where a.city in ?1 and a.creationDate = ?2")
    List<Account> findByCityInAndCreationDate(Collection<String> cities, Date creationDate);
    @Query("select a from Account a where a.city in ?1 order by a.creationDate DESC")
    List<Account> findByCityInOrderByCreationDateDesc(Collection<String> cities);
    @Query("select a from Account a where a.city in ?1 and a.creationDate = ?2 order by a.creationDate DESC")
    List<Account> findByCityInAndCreationDateOrderByCreationDateDesc(Collection<String> cities, Date creationDate);
    @Query("select a from Account a where a.city in ?1 order by a.creationDate")
    List<Account> findByCityInOrderByCreationDateAsc(Collection<String> cities);
    @Query("select a from Account a where a.city in ?1 and a.creationDate = ?2 order by a.creationDate")
    List<Account> findByCityInAndCreationDateOrderByCreationDateAsc(Collection<String> cities, Date creationDate);
    @Query("select a from Account a order by a.creationDate DESC")
    List<Account> findAllOrderByCreationDateDesc();
    @Query("select a from Account a order by a.creationDate")
    List<Account> findAllOrderByCreationDateAsc();
    @Query("select a from Account a where a.creationDate = ?1")
    List<Account> findByCreationDate(Date creationDate);
    @Query("select a from Account a where a.creationDate = ?1 order by a.creationDate DESC")
    List<Account> findByCreationDateOrderByCreationDateDesc(Date creationDate);
    @Query("select a from Account a where a.creationDate = ?1 order by a.creationDate")
    List<Account> findByCreationDateOrderByCreationDateAsc(Date creationDate);


}