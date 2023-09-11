package com.cardmonix.cardmonix.domain.repository;

import com.cardmonix.cardmonix.domain.entity.coins.Coin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin,Long> {

    Optional<Coin> findByName(String coin);

    Coin findCoinByName(String name);
    Page<Coin> findByNameContainingIgnoreCaseAndActivateIsTrue(String coinName, Pageable pageable);

    Page<Coin> findByActivateIsTrue(Pageable pageable);

}
