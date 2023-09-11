package com.cardmonix.cardmonix.service;

import com.cardmonix.cardmonix.domain.entity.token.OTPConfirmation;
import com.cardmonix.cardmonix.request.OtpValidateRequest;

public interface OtpService {

    void saveOtp(OTPConfirmation confirmationToken);
    String verifyUserOtp(OtpValidateRequest otpValidateRequest);
}
