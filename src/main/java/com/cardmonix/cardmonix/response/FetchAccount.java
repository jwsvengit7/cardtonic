package com.cardmonix.cardmonix.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FetchAccount {
    private Boolean status;
    private String message;
    private Accounts data;
}
