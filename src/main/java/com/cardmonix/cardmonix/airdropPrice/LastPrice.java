package com.cardmonix.cardmonix.airdropPrice;

import com.cardmonix.cardmonix.domain.entity.userModel.Balance;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import com.cardmonix.cardmonix.domain.repository.BalanceRepository;
import com.cardmonix.cardmonix.service.Implementation.authImpl.AuthServiceImpl;
import com.cardmonix.cardmonix.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@RequiredArgsConstructor
public class LastPrice implements ApplicationRunner {
    private final BalanceRepository balanceRepository;
    private final AuthServiceImpl authService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = authService.verifyUser(UserUtils.getAccessTokenEmail());
        Balance balance = balanceRepository.findBalanceByUser(user);

    }
}
