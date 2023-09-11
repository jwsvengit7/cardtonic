package com.cardmonix.cardmonix.service;

import com.cardmonix.cardmonix.request.SaleGiftcardRequest;
import org.springframework.web.multipart.MultipartFile;

public interface SellGiftcardService {

    String saleGiftcards(SaleGiftcardRequest saleGiftcardRequest, MultipartFile file);
}
