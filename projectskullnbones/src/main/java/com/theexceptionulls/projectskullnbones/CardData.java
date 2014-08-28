package com.theexceptionulls.projectskullnbones;

public class CardData {

    public static final String CARD_NUMBER = "cardNumber";
    public static final String RETAILER_NAME = "retailerName";

    private String cardNumber;
    private String retailerName;

    public CardData() {
    }

    public CardData(String cardNumber, String retailerName) {
        this.cardNumber = cardNumber;
        this.retailerName = retailerName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }
}
