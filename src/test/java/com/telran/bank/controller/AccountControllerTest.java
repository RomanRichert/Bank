package com.telran.bank.controller;

import com.telran.bank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.telran.bank.util.DtoCreator.getAccountResponseDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    void createAccount() {

    }

    @Test
    void getAllAccounts() throws Exception {
        when(accountService.getAllAccounts(any(), any(), any())).thenReturn(List.of(getAccountResponseDTO()));
        mvc.perform(get("/accounts"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAccount() {
    }

    @Test
    void patchAccount() {
    }
}