package com.theexceptionulls.projectskullnbones;

import android.net.Uri;

import java.io.Serializable;

public class CardData implements Serializable {

    private static final long serialVersionUID = 564971333;
    public static final String OUTPUT_FILE_NAME = "catCardDataList";

    private String cardNumber;
    private int retailerId;
    private String photoUri;

    public CardData(String cardNumber, int retailerId) {
        this.cardNumber = cardNumber;
        this.retailerId = retailerId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String uri) {
        this.photoUri = uri;
    }

    public int getRetailerId() {
        return retailerId;
    }

}
