package com.cardmonix.cardmonix.domain.entity.userModel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallets")
public class CoinWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;
    private Double wallet_amount;
    private Float walletInUsd;
    private String coin;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public CoinWallet(Float walletInUsd,Double wallet_amount,String coin){
        this.wallet_amount=wallet_amount;
        this.walletInUsd=walletInUsd;
        this.coin=coin;
    }
}
