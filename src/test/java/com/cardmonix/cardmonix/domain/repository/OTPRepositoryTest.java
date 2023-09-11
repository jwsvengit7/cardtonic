package com.cardmonix.cardmonix.domain.repository;

import com.cardmonix.cardmonix.domain.constant.Role;
import com.cardmonix.cardmonix.domain.entity.token.OTPConfirmation;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OTPRepositoryTest {
    @Autowired
    public OTPRepository otpRepository;
    @Autowired
    public UserRepository userRepository;

    @Test
    @Transactional
    public void findByUser_IdAndOtp(){
        User user  = saveUser();
        String otpValue = UUID.randomUUID().toString().substring(0,4);
        OTPConfirmation save = SvaveOtp(user,otpValue);
        OTPConfirmation otp = otpRepository.findByUser_IdAndOtp_generator(user.getId(),otpValue);
        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(otp).isNotNull();
    }

    @Test
    public void findByUserTest(){
        User user  = saveUser();
        OTPConfirmation save = SvaveOtp(user,UUID.randomUUID().toString().substring(0,4));
        OTPConfirmation otpConfirmation = otpRepository.findByUser(user);
        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(otpConfirmation).isNotNull();

    }
    private User saveUser(){
        return userRepository.save( User.builder()
                .user_name("jwsven")
                .activate(false)
                .email("chiorlujack@gmail.com")
                .role(Role.USER)
                .password("1234")
                .build());
    }
    private OTPConfirmation SvaveOtp(User user,String otp){
        return otpRepository.save(new OTPConfirmation(otp,user));
    }
}
