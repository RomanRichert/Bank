package com.telran.bank.repository;

import com.telran.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Transactional
    @Modifying
    @Query("update Account a set a.email = ?1, a.firstName = ?2, a.lastName = ?3, a.country = ?4, a.city = ?5 " +
            "where a.id = ?6")
    void updateAccountById(String email, String firstName, String lastName, String country, String city, @NonNull String id);

    @Transactional
    @Modifying
    @Query("update Account a set a.amountOfMoney = ?1 where a.id = ?2")
    void updateAmountOfMoneyById(@NonNull BigDecimal amountOfMoney, @NonNull String id);

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

    Account findById(String id);
}