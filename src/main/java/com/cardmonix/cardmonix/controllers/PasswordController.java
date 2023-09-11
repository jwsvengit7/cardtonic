package com.cardmonix.cardmonix.controllers;

import com.cardmonix.cardmonix.request.LoginRequest;
import com.cardmonix.cardmonix.response.ApiResponse;
import com.cardmonix.cardmonix.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/password")
public class PasswordController {
    private final PasswordService passwordService;
    @CrossOrigin("*")
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody LoginRequest loginRequest){
        ApiResponse<String> apiResponse = new ApiResponse<>(passwordService.forget_password(loginRequest));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}
