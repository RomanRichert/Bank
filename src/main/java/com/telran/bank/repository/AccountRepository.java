package com.telran.bank.repository;

import com.telran.bank.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;


import java.util.Date;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select a from Account a order by a.creationDate DESC")
    List<Account> findByOrderByCreationDateDesc();
    @Query("select a from Account a order by a.creationDate")
    List<Account> findByOrderByCreationDateAsc();
    @Query("select a from Account a where a.creationDate = ?1")
    List<Account> findByCreationDate(Date creationDate);
    @Query("select a from Account a where upper(a.city) = upper(?1)")
    List<Account> findByCity(List<String> city);
    @Query("select a from Account a where a.creationDate = ?1 and upper(a.city) = upper(?2)")
    List<Account> findByCreationDateAndCity(Date creationDate, List<String> city);
    @Query("select a from Account a where a.creationDate = ?1 order by a.creationDate DESC")
    List<Account> findByCreationDateOrderByCreationDateDesc(Date creationDate);
    @Query("select a from Account a where upper(a.city) = upper(?1) order by a.creationDate DESC")
    List<Account> findByCityOrderByCreationDateDesc(List<String> city);
    @Query("select a from Account a where a.creationDate = ?1 and upper(a.city) = upper(?2) order by a.creationDate DESC")
    List<Account> findByCreationDateAndCityOrderByCreationDateDesc(Date creationDate, List<String> city);
    @Query("select a from Account a where a.creationDate = ?1 order by a.creationDate")
    List<Account> findByCreationDateOrderByCreationDateAsc(Date creationDate);
    @Query("select a from Account a where upper(a.city) = upper(?1) order by a.creationDate")
    List<Account> findByCityOrderByCreationDateAsc(List<String> city);
    @Query("select a from Account a where a.creationDate = ?1 and upper(a.city) = upper(?2) order by a.creationDate")
    List<Account> findByCreationDateAndCityOrderByCreationDateAsc(Date creationDate, List<String> city);


}