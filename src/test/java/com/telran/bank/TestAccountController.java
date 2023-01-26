package com.telran.bank;


import com.telran.bank.entity.Account;
import com.telran.bank.controller.AccountController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAccountController {
    @Autowired
    private AccountController accountController;
    @Test
    public void createAccountTest(){
        Account account = new Account("dark@in.ru", "Roman", "Richert", "Germany", "Bonn");

        Assertions.assertEquals(account, accountController.createAccount(account));
    }
}
