package com.cardmonix.cardmonix.domain.repository;

import com.cardmonix.cardmonix.domain.entity.account.Account;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRespository extends JpaRepository<Account,Long> {

    Optional<Account>  findAccountByUser(User user);
    void deleteByUser(User user);
}
