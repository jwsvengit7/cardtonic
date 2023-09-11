package com.cardmonix.cardmonix.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserRequestDTO {
    private String user_name;
    private Date dob;
    private String phone;

}
