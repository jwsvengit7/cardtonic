package com.cardmonix.cardmonix.domain.repository;

import com.cardmonix.cardmonix.domain.entity.userModel.CoinWallet;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinWalletRepository extends JpaRepository<CoinWallet,Long> {
    List<CoinWallet> findCoinWalletByUser(User user);

}
