package com.cardmonix.cardmonix.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAccount {

    private String bankName;
    private String accountNumber;
    private String accountName;

}
