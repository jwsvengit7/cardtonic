package com.cardmonix.cardmonix.domain.entity.account;

import com.cardmonix.cardmonix.domain.entity.userModel.User;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Table(name = "account")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String accountName;
    private String accountNumber;
    private String bankName;
    private String subaccount_code;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id") // Assuming a column named "user_id" in the account table
    private User user;

}
