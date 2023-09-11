package com.cardmonix.cardmonix.domain.repository;

import com.cardmonix.cardmonix.domain.constant.Currency;
import com.cardmonix.cardmonix.domain.constant.Role;
import com.cardmonix.cardmonix.domain.entity.userModel.Balance;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BalanceRepositoryTest {
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private  UserRepository userRepository;

    @Test
    public void checkUserBalance(){
        User user = saveUser();
        Balance balance = balanceRepository.findBalanceByUser(user);
        Assertions.assertThat(balance).isNotNull();
    }

    public User saveUser(){
        User user = User.builder()
                        .user_name("jwsven")
                        .activate(false)
                        .email("chiorlujack@gmail.com")
                .role(Role.USER)
                .password("1234")
                .build();
        Balance balance = balanceRepository.save(Balance.builder()
                        .amount(65D)
                        .user(user)
                        .currency(Currency.USD)
                .build());
        user.setBalance(balance);
        return userRepository.save(user);
    }
}
