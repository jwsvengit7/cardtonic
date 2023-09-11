package com.cardmonix.cardmonix.domain.repository;

import com.cardmonix.cardmonix.domain.entity.token.OTPConfirmation;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OTPRepository extends JpaRepository<OTPConfirmation,Long> {
    @Query(value = "SELECT * FROM otp_confirmation WHERE user_id=?1 and otp_generator=?2",nativeQuery = true)
    OTPConfirmation findByUser_IdAndOtp_generator(Long user_id,String otp);


    OTPConfirmation findByUser(User user);
}
