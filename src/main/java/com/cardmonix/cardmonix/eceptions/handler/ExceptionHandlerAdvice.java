package com.cardmonix.cardmonix.eceptions.handler;

import com.cardmonix.cardmonix.eceptions.*;
import com.cardmonix.cardmonix.response.ApiResponse;
import com.cardmonix.cardmonix.response.CadiocExceptionsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<CadiocExceptionsResponse>> productNotfoundException(ProductNotFoundException error){
        CadiocExceptionsResponse cadiocExceptionsResponse = CadiocExceptionsResponse.builder()
                .time(convertDateResponse(LocalDateTime.now()))
                .message(error.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        ApiResponse<CadiocExceptionsResponse> apiResponse = new ApiResponse<>(cadiocExceptionsResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ApiResponse<CadiocExceptionsResponse>> passwordNotFound(PasswordException e){
        CadiocExceptionsResponse cadiocExceptionsResponse = CadiocExceptionsResponse.builder()
                .time(convertDateResponse(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        ApiResponse<CadiocExceptionsResponse> apiResponse = new ApiResponse<>(cadiocExceptionsResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiResponse<CadiocExceptionsResponse>> accountNotFound(AccountNotFoundException error){
        CadiocExceptionsResponse cadiocExceptionsResponse = CadiocExceptionsResponse.builder()
                .time(convertDateResponse(LocalDateTime.now()))
                .message(error.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        ApiResponse<CadiocExceptionsResponse> apiResponse = new ApiResponse<>(cadiocExceptionsResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CoinNotFoundException.class)
    public ResponseEntity<ApiResponse<CadiocExceptionsResponse>> coinNotFound(CoinNotFoundException error){
        CadiocExceptionsResponse cadiocExceptionsResponse = CadiocExceptionsResponse.builder()
                .time(convertDateResponse(LocalDateTime.now()))
                .message(error.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        ApiResponse<CadiocExceptionsResponse> apiResponse = new ApiResponse<>(cadiocExceptionsResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<CadiocExceptionsResponse>> notfoundException(UserNotFoundException error){
        CadiocExceptionsResponse cadiocExceptionsResponse = CadiocExceptionsResponse.builder()
                .time(convertDateResponse(LocalDateTime.now()))
                .message(error.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        ApiResponse<CadiocExceptionsResponse> apiResponse = new ApiResponse<>(cadiocExceptionsResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BankNotFoundException.class)
    public ResponseEntity<ApiResponse<CadiocExceptionsResponse>> bankNotFound(BankNotFoundException error){
        CadiocExceptionsResponse cadiocExceptionsResponse = CadiocExceptionsResponse.builder()
                .time(convertDateResponse(LocalDateTime.now()))
                .message(error.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        ApiResponse<CadiocExceptionsResponse> apiResponse = new ApiResponse<>(cadiocExceptionsResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundException.class)
    public ResponseEntity<ApiResponse<CadiocExceptionsResponse>> bankNotFound(InsufficientFundException error){
        CadiocExceptionsResponse cadiocExceptionsResponse = CadiocExceptionsResponse.builder()
                .time(convertDateResponse(LocalDateTime.now()))
                .message(error.getMessage())
                .statusCode(HttpStatus.INSUFFICIENT_STORAGE.value())
                .build();
        ApiResponse<CadiocExceptionsResponse> apiResponse = new ApiResponse<>(cadiocExceptionsResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.INSUFFICIENT_STORAGE);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<CadiocExceptionsResponse>> invalid(InvalidCredentialsException error
                                                                                   ){
        CadiocExceptionsResponse cadiocExceptionsResponse = CadiocExceptionsResponse.builder()
                .time(convertDateResponse(LocalDateTime.now()))
                .message(error.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
        ApiResponse<CadiocExceptionsResponse> apiResponse = new ApiResponse<>(cadiocExceptionsResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserNotEnabledException.class)
    public ResponseEntity<ApiResponse<CadiocExceptionsResponse>> userNotEnableException(UserNotEnabledException error){
        CadiocExceptionsResponse cadiocExceptionsResponse = CadiocExceptionsResponse.builder()
                .time(convertDateResponse(LocalDateTime.now()))
                .message(error.getMessage())
                .statusCode(HttpStatus.LOCKED.value())
                .build();
        ApiResponse<CadiocExceptionsResponse> apiResponse = new ApiResponse<>(cadiocExceptionsResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.LOCKED);
    }
    @ExceptionHandler(AccountIsuesException.class)
    public ResponseEntity<ApiResponse<CadiocExceptionsResponse>> apiResponseResponseEntity(AccountIsuesException e){
        CadiocExceptionsResponse cadiocExceptionsResponse = CadiocExceptionsResponse.builder()
                .time(convertDateResponse(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
        ApiResponse<CadiocExceptionsResponse> apiResponse = new ApiResponse<>(cadiocExceptionsResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }


    private String convertDateResponse(LocalDateTime dateTime){
            return dateTime.toString();
    }
}
