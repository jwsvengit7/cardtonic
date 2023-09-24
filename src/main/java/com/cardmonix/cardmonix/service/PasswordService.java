package com.cardmonix.cardmonix.service;

import com.cardmonix.cardmonix.request.ChangePasswordRequest;
import com.cardmonix.cardmonix.request.LoginRequest;

public interface PasswordService {
    String forget_password(LoginRequest passwordchange);
    String changePassword(ChangePasswordRequest changePasswordRequest);

}
