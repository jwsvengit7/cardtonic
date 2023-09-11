package com.cardmonix.cardmonix.domain.repository;

import com.cardmonix.cardmonix.domain.constant.Status;
import com.cardmonix.cardmonix.domain.entity.account.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepositRepository extends JpaRepository<Deposit,Long> {

     Optional<Deposit> findByDepositIdAndStatus(Long id, Status status);
}
