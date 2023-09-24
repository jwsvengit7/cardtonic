package com.cardmonix.cardmonix.service;

import com.cardmonix.cardmonix.request.AccountRequest;
import com.cardmonix.cardmonix.request.AccountRequestDTO;
import com.cardmonix.cardmonix.request.BvnRequest;
import com.cardmonix.cardmonix.request.RequestAccount;
import com.cardmonix.cardmonix.response.AccountResponse;
import com.cardmonix.cardmonix.response.Accounts;
import com.cardmonix.cardmonix.response.FetchAccount;

public interface AccountService {

    FetchAccount getBankCodeAndSend(String bankName, String accountNumber);
    String saveAccount(RequestAccount requestAccount);
    String updateAccount(AccountRequestDTO accountRequest);
    Void deleteAccount(String subaccount_code);
    Accounts getAccountByLogedInUser(Long userId);
}
