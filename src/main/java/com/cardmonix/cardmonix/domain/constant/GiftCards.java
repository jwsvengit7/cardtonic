package com.cardmonix.cardmonix.domain.constant;

public enum GiftCards {
    AMAZON("Amazon",3533D,"https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Amazon_logo.svg/603px-Amazon_logo.svg.png"),
    AMAZON_GIFT_CARD("Amazon Gift Card",3533D,"https://www.zooinfotech.com/wp-content/uploads/2020/03/Amazon-Gift-Card.jpg"),
    USA_Apple_ITunes_Gift_Card("USA Apple iTunes Gift Card",3533D,"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcROMkim_RlXt3FdoaZ0Hg9PqsDDsUSubBUVRhGJ7U96ZrS_sKhq"),
    STEAM_GIFT_CARD("Steam $50 Gift Card",3533D,"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcT0pnsJT4xBu1ZRM2Ap_fg1n8ZAVK1Np5EQFSGy5dT0DkS3PLwp"),
    VISA("Visa",3533D,"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcR9MglbmeJTP2C-gsNJVFsa950EefLh3DNJHLaUUXW5VlTJ8AGX"),
    SEPHORA_GIFT_CARD("Sephora Gift Card",3533D,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTeo2F2UWCVO23u7MC5qBOmKzDbe_1-q8I_vx2kE92aXg3-PL66"),
    VISA_GIFT_CARD("Visa Gift Card",3533D,"https://productimages.nimbledeals.com/nimblebuy/100-visa-gift-card-1-53190-regular.jpg"),
    EBAY_GIFT_CARD_US("Ebay Gift Card US",3533D,"https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT_129TB5shN6gHk6J5a6bfxgDw9qMMEfKTWgdzDQtShiuNzQgW"),
    RAZER_GOLD_GAME_CARD("Razer Gold Game Card",3533D,"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTRTTm2311v_HHj5cPi6jUb8xL-mTSza8cmAEZOUTzPrH5YA1t-"),
    SONY_PSN_CARD("Sony PSN Card",3533D,"https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTe9Dej5LaVyV-b8jmzjVf7g1L9Zf9MxVPS3wKMtsmzqboZ5Df-"),
    STARBUCKS_GIFT_CARD("Starbucks Gift Card",3533D,"https://i5.walmartimages.com/asr/676eb541-09cc-42d6-9c17-9abd94daa574.939caf08f74a37f1ab407f3053aeb340.jpeg"),
    FOOT_LOCKER_GIFT_CARD("Foot Locker Gift Card",3533D,"https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcR5frc2oVGweeVkdPaieswYu8aSuuY-nafMAtI-BDZV5fljkPwQ"),
    XBOX_GIFT_CARD("Xbox Gift Card",200D,"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcR9MPcf5d67owPPYXureN0MEWrwEw5jBB96iMa2yONLaqHdXhYe"),
    WALMART_GIFT_CARD("Walmart Gift Card",434D,"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcROMkim_RlXt3FdoaZ0Hg9PqsDDsUSubBUVRhGJ7U96ZrS_sKhq");
    private final String type;
    private final Double amount;
    private final String image;
    GiftCards(String type,Double amount,String image) {
        this.type = type;
        this.amount=amount;
        this.image=image;
    }
    public static String toType(String type) {
        for (GiftCards giftCards : GiftCards.values()) {
            if (giftCards.type.equalsIgnoreCase(type)) {
                return giftCards.getType();
            }
        }
        throw new IllegalArgumentException("You must select a giftcard");
    }
    public String getType() {
        return type;
    }
    public String getImage() {
        return image;
    }
    public Double getAmount() {
        return amount;
    }



}
