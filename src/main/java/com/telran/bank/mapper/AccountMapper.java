package com.telran.bank.mapper;

import com.telran.bank.dto.AccountDTO;
import com.telran.bank.entity.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO toDTO(Account account);

    Account toEntity(AccountDTO accountDTO);

    List<AccountDTO> accountsToAccountDTOs(List<Account> accounts);
}