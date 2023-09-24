package com.cardmonix.cardmonix.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
