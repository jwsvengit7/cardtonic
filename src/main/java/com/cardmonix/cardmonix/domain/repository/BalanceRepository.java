package com.cardmonix.cardmonix.domain.repository;

import com.cardmonix.cardmonix.domain.entity.userModel.Balance;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance,Long> {
    Balance findBalanceByUser(User user);

}
