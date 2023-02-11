package com.telran.bank.mapper;

import com.telran.bank.dto.AccountRequestDTO;
import com.telran.bank.dto.AccountResponseDTO;
import com.telran.bank.entity.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponseDTO toResponseDTO(Account account);

    Account toEntity(AccountRequestDTO accountRequestDTO);

    List<AccountResponseDTO> accountsToAccountResponseDTOs(List<Account> accounts);
}