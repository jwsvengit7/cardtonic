package com.cardmonix.cardmonix.controllers;

import com.cardmonix.cardmonix.request.AccountRequestDTO;
import com.cardmonix.cardmonix.request.BvnRequest;
import com.cardmonix.cardmonix.request.RequestAccount;
import com.cardmonix.cardmonix.response.AccountResponse;
import com.cardmonix.cardmonix.response.Accounts;
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
    @GetMapping("/verifyAccount")
    public ResponseEntity<ApiResponse<FetchAccount>> getBankDetails(@RequestParam("bankName") String bankName, @RequestParam("accountNumber") String accountNumber){
        ApiResponse<FetchAccount> account = new ApiResponse<>(accountService.getBankCodeAndSend(bankName,accountNumber));
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @CrossOrigin("*")
    @PostMapping(value="/saveAccount")
    public ResponseEntity<ApiResponse<String>> saveSubAccount(@RequestBody RequestAccount requestAccount){
        ApiResponse<String> getDetails = new ApiResponse<>(accountService.saveAccount(requestAccount));
        return new ResponseEntity<>(getDetails, HttpStatus.OK);
    }

    @CrossOrigin("*")
    @GetMapping("get-my-account/{userId}")
    public ResponseEntity<ApiResponse<Accounts>> account(@PathVariable Long userId){
        ApiResponse<Accounts> apiResponse = new ApiResponse<>(accountService.getAccountByLogedInUser(userId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @CrossOrigin("*")
    @PostMapping("update-account")
    public ResponseEntity<ApiResponse<String>> account(@RequestBody AccountRequestDTO accountRequestDTO){
        ApiResponse<String> apiResponse = new ApiResponse<>(accountService.updateAccount(accountRequestDTO));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @CrossOrigin("*")
    @DeleteMapping("/deleteAccount/{subaccount_code}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable String subaccount_code){
        ApiResponse<Void> apiResponse = new ApiResponse<>(accountService.deleteAccount(subaccount_code));
        return new ResponseEntity<>(apiResponse,HttpStatus.LOCKED);
    }
}
