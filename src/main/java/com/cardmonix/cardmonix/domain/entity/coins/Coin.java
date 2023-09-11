package com.cardmonix.cardmonix.domain.entity.coins;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coin")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coin_id;
    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "image",nullable = false)
    private String image;
    @Column(name = "current_price",nullable = false)
    private Float current_price;
    @Column(name = "old_price",nullable = false)
    private Float old_price;

    private Boolean activate;

    public Coin(String name, Float currentPrice, String image,Float old_price) {
        this.name=name;
        this.image=image;
        this.current_price=currentPrice;
        this.old_price=old_price;
    }
}
