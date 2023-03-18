package com.telran.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.telran.bank.util.DtoCreator.getAccountRequestDTO;
import static com.telran.bank.util.DtoCreator.getPatchingAccountRequestDTO;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @Test
    void createAccount() throws Exception {
        mvc.perform(post("/accounts")
                        .content(new ObjectMapper().writeValueAsString(getAccountRequestDTO()))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllAccounts() throws Exception {
        mvc.perform(get("/accounts")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAccount() throws Exception {
        mvc.perform(get("/accounts/1")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void patchAccount() throws Exception {
        mvc.perform(patch("/accounts/1")
                        .content(new ObjectMapper().writeValueAsString(getPatchingAccountRequestDTO()))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}