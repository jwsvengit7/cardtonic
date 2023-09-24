package com.cardmonix.cardmonix.controllers;

import com.cardmonix.cardmonix.response.Accounts;
import com.cardmonix.cardmonix.response.FetchAccount;
import com.cardmonix.cardmonix.security.JWTAuthenticationFilter;
import com.cardmonix.cardmonix.service.AccountService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = AccountControllers.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)

class AccountControllersTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private AccountService accountService;
    private FetchAccount fetchAccount;

    @BeforeEach
    public void init(){
        fetchAccount = new FetchAccount(true,
                "message resolve",
                new Accounts(
                        "TEMPLE JACK CHIORLU WILLIAM",
                "0794940296",
                "Access Bank"));
    }

    @Test
    void getBankDetails() throws Exception{
        String accountNumber = "0794940296";
        when(accountService.getBankCodeAndSend(fetchAccount.getData().getBankName(),accountNumber)).thenReturn(fetchAccount);
        mockMvc.perform(get("/api/v1/account/verifyAccount").param("bankName",fetchAccount.getData().getBankName()).param("accountNumber",accountNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.data.account_name", CoreMatchers.is(accountNumber)))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void saveSubAccount() {
    }

    @Test
    void verifyCard() {
    }

    @Test
    void account() {
    }
}