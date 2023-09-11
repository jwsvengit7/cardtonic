package com.cardmonix.cardmonix.controllers;

import com.cardmonix.cardmonix.request.BvnRequest;
import com.cardmonix.cardmonix.request.RequestAccount;
import com.cardmonix.cardmonix.response.AccountResponse;
import com.cardmonix.cardmonix.response.ApiResponse;
import com.cardmonix.cardmonix.response.FetchAccount;
import com.cardmonix.cardmonix.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/account")
@RestController
@RequiredArgsConstructor
public class AccountControllers {
    private final AccountService accountService;
    @CrossOrigin("*")
    @GetMapping("verifyAccount")
    public ResponseEntity<ApiResponse<FetchAccount>> getBankDetails(@RequestParam("bankName") String bankName, @RequestParam("accountNumber") String accountNumber){
        ApiResponse<FetchAccount> account = new ApiResponse<>(accountService.getBankCodeAndSend(bankName,accountNumber));
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @CrossOrigin("*")
    @PostMapping("saveAccount")
    public ResponseEntity<ApiResponse<RequestAccount>> saveSubAccount(@RequestBody RequestAccount requestAccount){
        ApiResponse<RequestAccount> getDetails = new ApiResponse<>(accountService.saveAccount(requestAccount));
        return new ResponseEntity<>(getDetails, HttpStatus.OK);
    }
    @CrossOrigin("*")
    @PostMapping("verifyCard")
    public ResponseEntity<ApiResponse<String>> verifyCard(@RequestBody BvnRequest bvnRequest){
        ApiResponse<String> verifyCard = new ApiResponse<>(accountService.verifyCard(bvnRequest));
        return new ResponseEntity<>(verifyCard, HttpStatus.OK);
    }

    @CrossOrigin("*")
    @GetMapping("get-my-account")
    public ResponseEntity<ApiResponse<AccountResponse>> account(){
        ApiResponse<AccountResponse> apiResponse = new ApiResponse<>(accountService.getAccountByLogedInUser());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
