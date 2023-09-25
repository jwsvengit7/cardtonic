package com.cardmonix.cardmonix.response;

import com.cardmonix.cardmonix.domain.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositReponse {
    private Long depositId;
    private Float amountInCurrency;
    private Double amount;
    private String coin;
    private Status status;
    private String image;
    private String transId;
    private String proof;
}
