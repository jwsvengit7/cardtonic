package com.cardmonix.cardmonix.service.Implementation.otpImpl;

import com.cardmonix.cardmonix.domain.entity.token.OTPConfirmation;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import com.cardmonix.cardmonix.domain.repository.OTPRepository;
import com.cardmonix.cardmonix.domain.repository.UserRepository;
import com.cardmonix.cardmonix.eceptions.UserNotFoundException;
import com.cardmonix.cardmonix.events.RegisterPublish;
import com.cardmonix.cardmonix.request.OtpValidateRequest;
import com.cardmonix.cardmonix.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ConfirmEmailMessageImpl implements OtpService {
    private final OTPRepository otpRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void saveOtp(OTPConfirmation confirmationToken) {
        otpRepository.save(confirmationToken);
    }
    public void sendOtp(User user,String otp,OTPConfirmation confirmationToken){
        System.out.println("***********\n ***************\n ************");
        OTPConfirmation otpConfirmation = otpRepository.findByUser(user);
        if (otpConfirmation != null){
            otpRepository.delete(otpConfirmation);
        }
        saveOtp(confirmationToken);
        System.out.println(otp);
        applicationEventPublisher.publishEvent(new RegisterPublish(user,otp));
    }

    @Override
    public String verifyUserOtp(OtpValidateRequest otpValidateRequest) {
        User user = userRepository.findByEmail(otpValidateRequest.getEmail()).orElseThrow(()->new UserNotFoundException("USER NOT FOUND"));
        OTPConfirmation otpConfirmation = otpRepository.findByUser_IdAndOtp_generator(user.getId(), otpValidateRequest.getOtp());

        if (otpConfirmation != null && !isOtpExpired(otpConfirmation)) {

            otpConfirmation.getUser().setActivate(true);

            userRepository.save(otpConfirmation.getUser());
            return otpConfirmation.getUser().getEmail()+" Have been verified";
        } else {
            return "Invalid or expired OTP";
        }
    }

    private boolean isOtpExpired(OTPConfirmation otpConfirmation) {
        LocalDateTime otpCreationTime = otpConfirmation.getExpiresAt();
        LocalDateTime currentDateTime = LocalDateTime.now();

        Duration duration = Duration.between(otpCreationTime, currentDateTime);
        long minutesPassed = duration.toMinutes();

        System.out.println(minutesPassed);
        long otpExpirationMinutes = 4;
        return minutesPassed > otpExpirationMinutes;
    }

}
