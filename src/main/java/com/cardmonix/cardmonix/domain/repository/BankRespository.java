package com.cardmonix.cardmonix.domain.repository;

import com.cardmonix.cardmonix.domain.entity.account.Banks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRespository extends JpaRepository<Banks,Long> {
    Optional<Banks> findBanksByBankName(String bankName);
}
