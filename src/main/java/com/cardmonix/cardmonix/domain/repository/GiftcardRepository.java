package com.cardmonix.cardmonix.domain.repository;

import com.cardmonix.cardmonix.domain.entity.coins.Giftcard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftcardRepository extends JpaRepository<Giftcard,Long> {
}
