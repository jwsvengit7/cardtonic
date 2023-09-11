package com.cardmonix.cardmonix.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFromUser {
    private String email;
    private String username;
    private boolean activate;
    private String dob;
    private Object responseAccess;
}
