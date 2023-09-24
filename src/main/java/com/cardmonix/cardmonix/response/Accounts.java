package com.cardmonix.cardmonix.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {
    private String account_number;
    private String account_name;
    private String bankName;

}
