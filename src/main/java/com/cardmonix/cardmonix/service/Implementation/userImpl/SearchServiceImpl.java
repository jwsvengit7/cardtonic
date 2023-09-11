package com.cardmonix.cardmonix.service.Implementation.userImpl;

import com.cardmonix.cardmonix.domain.entity.coins.Coin;
import com.cardmonix.cardmonix.domain.repository.CoinRepository;
import com.cardmonix.cardmonix.response.CoinResponse;
import com.cardmonix.cardmonix.service.SearchService;
import com.cardmonix.cardmonix.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final CoinRepository coinRepository;
    private final ModelMapper modelMapper;


    @Override
    public PaginationUtils viewCoin(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Coin> coinPage = coinRepository.findByActivateIsTrue(pageable);

        List<Coin> coins = coinPage.getContent();

        return PaginationUtils.builder()
                .content(coins)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(coinPage.getTotalElements())
                .totalPage(coinPage.getTotalPages())
                .isLast(!coinPage.hasNext())
                .build();
    }

    @Override
    public PaginationUtils searchByCoin(Integer pageNo, String sortBy, String sortDir, Integer pageSize, String searchCoinName) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));

        Page<Coin> coinPage;

        if (StringUtils.hasText(searchCoinName)) {
            coinPage = coinRepository.findByNameContainingIgnoreCaseAndActivateIsTrue(searchCoinName, PageRequest.of(pageNo, pageSize, sort));
        } else {
            coinPage = coinRepository.findByActivateIsTrue(PageRequest.of(pageNo, pageSize, sort));
        }

        List<CoinResponse> response = coinPage.getContent().stream()
                .map(coinValue -> modelMapper.map(coinValue, CoinResponse.class))
                .collect(Collectors.toList());

        return PaginationUtils.builder()
                .content(response)
                .pageNo(coinPage.getNumber())
                .pageSize(coinPage.getSize())
                .totalElements(coinPage.getTotalElements())
                .totalPage(coinPage.getTotalPages())
                .isLast(coinPage.isLast())
                .build();
    }



}
