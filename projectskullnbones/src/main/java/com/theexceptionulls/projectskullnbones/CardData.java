package com.theexceptionulls.projectskullnbones;

import android.net.Uri;

public class CardData {

    public static final String CARD_NUMBER = "cardNumber";
    public static final String RETAILER_NAME = "retailerName";

    private String cardNumber;
    private String retailerName;
    private Uri photoUri;

    public CardData() {
    }

    public CardData(String cardNumber, String retailerName, Uri photoUri) {
        this.cardNumber = cardNumber;
        this.retailerName = retailerName;
        this.photoUri = photoUri;
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

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri uri) {
        this.photoUri = uri;
    }
}
