package com.cardmonix.cardmonix.service.Implementation.giftcardImpl;

import com.cardmonix.cardmonix.domain.constant.GiftCards;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class GiftcardServiceImpl {

    public List<GiftcardObject> getGiftcards(){
        List<GiftcardObject> allTypes = new ArrayList<>();
        for (GiftCards giftCard : GiftCards.values()) {
            allTypes.add(new GiftcardObject(giftCard.getImage(),giftCard.getAmount(), giftCard.getType()));
        }
        System.out.println(allTypes);
        return allTypes;
    }
}
