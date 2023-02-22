package com.telran.bank.mapper;

import com.telran.bank.dto.AccountResponseDTO;
import com.telran.bank.entity.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static com.telran.bank.util.DtoCreator.getAccountRequestDTO;
import static com.telran.bank.util.DtoCreator.getAccountResponseDTO;
import static com.telran.bank.util.EntityCreator.ACCOUNT1;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AccountMapperTest {

    AccountMapper accountMapper = new AccountMapperImpl();

    @Test
    @DisplayName("Tests mapping of the Account to AccountResponseDTO")
    void toResponseDTO() {
        assertEquals(getAccountResponseDTO(), accountMapper.toResponseDTO(ACCOUNT1), "Something went wrong by mapping Account to AccountResponseDTO");
    }

    @Test
    @DisplayName("Tests mapping of the AccountRequestDTO to Account")
    void toEntity() {
        int i = 0;
        Field[] fields = ACCOUNT1.getClass().getDeclaredFields();
        Field[] functionFields = accountMapper.toEntity(getAccountRequestDTO()).getClass().getDeclaredFields();

        while (i < fields.length) {
            if (i > 0) {
                assertEquals(fields[i], functionFields[i], "Something went wrong by mapping AccountRequestDTO to Account in field: " + i);
            }
            i++;
        }

    }

    @Test
    @DisplayName("Testing of mapping List<Account> to List<AccountResponseDTO>")
    void accountsToAccountResponseDTOs() {
        List<Account> accounts = List.of(ACCOUNT1);
        List<AccountResponseDTO> dtos = List.of(getAccountResponseDTO());

        assertEquals(dtos, accountMapper.accountsToAccountResponseDTOs(accounts), "Something went wrong by mapping List<Account> to List<AccountResponseDTO>");
    }
}