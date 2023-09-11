package com.cardmonix.cardmonix.service;

import com.cardmonix.cardmonix.request.AccountRequest;
import com.cardmonix.cardmonix.request.BvnRequest;
import com.cardmonix.cardmonix.request.RequestAccount;
import com.cardmonix.cardmonix.response.AccountResponse;
import com.cardmonix.cardmonix.response.FetchAccount;

public interface AccountService {

    FetchAccount getBankCodeAndSend(String bankName, String accountNumber);
    RequestAccount saveAccount(RequestAccount requestAccount);

    String verifyCard(BvnRequest bvnRequest);

    AccountResponse updateAccount(AccountRequest accountRequest);
    AccountResponse deleteAccount(String subaccount_code);
    AccountResponse  getAccountByLogedInUser();
}
