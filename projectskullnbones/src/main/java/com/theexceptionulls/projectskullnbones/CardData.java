package com.theexceptionulls.projectskullnbones;

import android.net.Uri;

public class CardData {

    public static final String CARD_NUMBER = "cardNumber";
    public static final String RETAILER_NAME = "retailerName";

    private String cardNumber;
    private String retailerName;
    private Uri photoUri;
    private String retailerOpco;

    public CardData() {
    }

    public CardData(String cardNumber, Uri photoUri, String retailerName, String retailerOpco) {
        this.cardNumber = cardNumber;
        this.retailerName = retailerName;
        this.photoUri = photoUri;
        this.retailerOpco = retailerOpco;
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

    public String getRetailerOpco() {
        return retailerOpco;
    }

    public void setRetailerOpco(String retailerOpco) {
        this.retailerOpco = retailerOpco;
    }
}
