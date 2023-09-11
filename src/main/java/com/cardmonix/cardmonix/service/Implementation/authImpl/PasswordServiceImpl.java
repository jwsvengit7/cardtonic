package com.cardmonix.cardmonix.service.Implementation.authImpl;

import com.cardmonix.cardmonix.domain.entity.userModel.User;
import com.cardmonix.cardmonix.domain.repository.OTPRepository;
import com.cardmonix.cardmonix.domain.repository.UserRepository;
import com.cardmonix.cardmonix.eceptions.UserNotFoundException;
import com.cardmonix.cardmonix.request.LoginRequest;
import com.cardmonix.cardmonix.service.Implementation.otpImpl.ConfirmEmailMessageImpl;
import com.cardmonix.cardmonix.service.PasswordService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final UserRepository userRepository;
    private final OTPRepository otpRepository;
    private final ConfirmEmailMessageImpl otpSetup;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String forget_password(LoginRequest passwordchange) {
        User user   = userRepository.findByEmail(passwordchange.getEmail())
                .orElseThrow(()-> new UserNotFoundException("USER NOT FOUND"));
        user.setPassword(passwordEncoder.encode(passwordchange.getPassword()));
        userRepository.save(user);
        return user.getUser_name()+" your password successful change";

    }


}
