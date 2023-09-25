package com.cardmonix.cardmonix.utils;

import com.cardmonix.cardmonix.domain.entity.coins.Coin;
import com.cardmonix.cardmonix.domain.entity.userModel.CoinWallet;
import com.cardmonix.cardmonix.domain.repository.CoinRepository;
import com.cardmonix.cardmonix.domain.repository.CoinWalletRepository;

import java.util.List;

public class DepositUtils {

    public static boolean checkUserCoinBalance(List<CoinWallet> coinWallets, String coin, Double amount, CoinRepository coinRepository, CoinWalletRepository coinWalletRepository) {
        Coin checkCoin = coinRepository.findCoinByName(coin);
        for (CoinWallet c : coinWallets) {
            if (c.getCoin().equals(coin)) {
                c.setWallet_amount(c.getWallet_amount() - amount);
                c.setWalletInUsd((float) (c.getWalletInUsd() - checkCoin.getCurrent_price() / amount));
                coinWalletRepository.save(c);
                return true;
            }
        }
        return false;
    }
}
