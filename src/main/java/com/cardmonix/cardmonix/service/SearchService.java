package com.cardmonix.cardmonix.service;

import com.cardmonix.cardmonix.utils.PaginationUtils;

public interface SearchService {
    PaginationUtils searchByCoin(Integer pageNo, String sortBy, String sortDir, Integer pageSize, String searchCoinName);
    PaginationUtils viewCoin(Integer pageNo, Integer pageSize);
}
