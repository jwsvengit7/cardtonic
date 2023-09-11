package com.cardmonix.cardmonix.domain.constant;



public enum SelectedInvestment {


    STARTER_PLAN("STARTER  PLAN"),
    LITE_PLAN("LITE  PLAN"),
    PROFESSIONAL_PLAN("PROFESSIONAL  PLAN"),
    EXPERT_PLAN("EXPERT  PLAN");

    private final String type;
    SelectedInvestment(String type) {
            this.type = type;
        }
        public static SelectedInvestment toType(String type) {
            for (SelectedInvestment category : SelectedInvestment.values()) {
                if (category.type.equalsIgnoreCase(type)) {
                    return category;
                }
            }
            throw new IllegalArgumentException("You must select a payment type: ");
        }









}
