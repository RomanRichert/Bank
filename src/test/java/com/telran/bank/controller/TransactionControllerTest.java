package com.telran.bank.controller;

import com.telran.bank.service.AccountService;
import com.telran.bank.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TransactionService transactionService;

    @MockBean
    AccountService accountService;

    @Test
    void getAllTransactions() throws Exception {
        mvc.perform(get("/transactions")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getTransaction() throws Exception {
        mvc.perform(get("/transactions/1")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}