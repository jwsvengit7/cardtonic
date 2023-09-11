package com.cardmonix.cardmonix.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {
    private String account_number;
    private String account_name;
    private Long bank_id;

}
