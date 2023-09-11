package com.cardmonix.cardmonix.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestObject {
    private String country;
    private String type;
    private String account_number;
    private String bvn;
    private String bank_code;
    private String first_name;
    private String last_name;

}
