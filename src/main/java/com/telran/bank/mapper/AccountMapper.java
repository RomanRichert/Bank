package com.telran.bank.mapper;

import com.telran.bank.dto.AccountRequestDTO;
import com.telran.bank.dto.AccountResponseDTO;
import com.telran.bank.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(componentModel = "spring",
        uses = {TransactionMapper.class},
        injectionStrategy = CONSTRUCTOR,
        imports = {LocalDate.class, BigDecimal.class})
public interface AccountMapper {

    AccountResponseDTO toResponseDTO(Account account);

    @Mapping(target = "creationDate", expression = "java(LocalDate.now())")
    @Mapping(target = "amountOfMoney", expression = "java(BigDecimal.ZERO)")
    Account toEntity(AccountRequestDTO accountRequestDTO);

    List<AccountResponseDTO> accountsToAccountResponseDTOs(List<Account> accounts);
}