package com.theexceptionulls.projectskullnbones;

import android.net.Uri;

import java.io.Serializable;

public class CardData implements Serializable {

    private static final long serialVersionUID = 564971333;
    public static final String OUTPUT_FILE_NAME = "catCardDataList";
    public static final String CARD_NUMBER = "cardNumber";
    public static final String RETAILER_ID = "retailerId";

    private String cardNumber;
    private int retailerId;
    private Uri photoUri;

    private CardData(){

    }

    public CardData(int retailerId) {
        this.retailerId = retailerId;
    }

    public CardData(String cardNumber, int retailerId) {
        this.cardNumber = cardNumber;
        this.retailerId = retailerId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri uri) {
        this.photoUri = uri;
    }

    public int getRetailerId() {
        return retailerId;
    }

}
