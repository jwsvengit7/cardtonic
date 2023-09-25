package com.cardmonix.cardmonix.domain.entity.account;

import com.cardmonix.cardmonix.domain.constant.Status;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "deposit")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositId;
    private LocalDateTime localDateTime;
    private Float amountInCurrency;
    private Double amount;
    private String coin;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String image;
    private String transId;

    private String proof;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
