package com.cardmonix.cardmonix.controllers;

import com.cardmonix.cardmonix.request.SaleGiftcardRequest;
import com.cardmonix.cardmonix.response.ApiResponse;
import com.cardmonix.cardmonix.service.Implementation.giftcardImpl.GiftcardObject;
import com.cardmonix.cardmonix.service.Implementation.giftcardImpl.GiftcardServiceImpl;
import com.cardmonix.cardmonix.service.SellGiftcardService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/giftcards")
public class GiftcardsController  {
    private final GiftcardServiceImpl giftcardService;
    private final SellGiftcardService sellGiftcardService;
    @CrossOrigin("*")
    @GetMapping("all-giftcard")
    public ResponseEntity<ApiResponse<List<GiftcardObject>>> getGiftcards(){
        ApiResponse<List<GiftcardObject>> apiResponse = new ApiResponse<>(giftcardService.getGiftcards());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @CrossOrigin("*")
    @PostMapping(value = "/create-giftcard",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> saleGiftcards(@RequestBody SaleGiftcardRequest request, @RequestParam("file") MultipartFile file){
        ApiResponse<String> apiResponse = new ApiResponse<>(sellGiftcardService.saleGiftcards(request,file));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }
}
