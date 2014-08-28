package com.theexceptionulls.projectskullnbones;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class AppSettings {

    private static AppSettings instance;
    public static final String SCAN_ENGINE_READY_INTENT = "com.catalinamarketing.scanengineready";
    private static List<CardData> cardDataList = new ArrayList<CardData>();

    private String[] storeList = {
            "A&P",
            "Family Express",
            "Giant",
            "Giant BONUSCARD",
            "Harris Teeter",
            "Kroger",
            "Marsh",
            "Martin's",
            "Safeway",
            "ShopRite",
            "Stop & Shop",
            "Walmart",
            "Weis"
    };

    private int[] storeListThumbnailsID = {
            R.drawable.i_aandp,
            R.drawable.i_familyexpress,
            R.drawable.i_gl,
            R.drawable.i_gc,
            R.drawable.i_harristeeter,
            R.drawable.i_kroger,
            R.drawable.i_marsh,
            R.drawable.i_martins,
            R.drawable.i_safeway,
            R.drawable.i_shoprite,
            R.drawable.i_stopandshop,
            R.drawable.i_walmart,
            R.drawable.i_weis
    };

    private AppSettings() {

        CardData cardData = new CardData();
        cardData.setCardNumber("334053486291");
        cardData.setRetailerName(storeList[0]);
        cardDataList.add(cardData);

        CardData cardData1 = new CardData();
        cardData1.setCardNumber("5027369230");
        cardData1.setRetailerName(storeList[1]);
        cardDataList.add(cardData1);

        CardData cardData2 = new CardData();
        cardData2.setCardNumber("440089052802");
        cardData2.setRetailerName(storeList[2]);
        cardDataList.add(cardData2);
    }

    public static AppSettings getInstance() {
        if (instance == null) {
            return instance = new AppSettings();
        } else {
            return instance;
        }
    }

    public String[] getStoreList() {
        return storeList;
    }

    public int[] getStoreListThumbnailsID() {
        return storeListThumbnailsID;
    }

    public List<CardData> getCardDataList() {
        return cardDataList;
    }

    public void addToCardDataList(CardData cardData) {
        cardDataList.add(cardData);
    }

    public static Drawable getDrawable(Context context, String retailerName){
        if (retailerName.equals(AppSettings.getInstance().getStoreList()[0])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[0]);
        }else if (retailerName.equals(AppSettings.getInstance().getStoreList()[1])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[1]);
        }else if (retailerName.equals(AppSettings.getInstance().getStoreList()[2])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[2]);
        }else if (retailerName.equals(AppSettings.getInstance().getStoreList()[3])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[3]);
        }else if (retailerName.equals(AppSettings.getInstance().getStoreList()[4])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[4]);
        }else if (retailerName.equals(AppSettings.getInstance().getStoreList()[5])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[5]);
        }else if (retailerName.equals(AppSettings.getInstance().getStoreList()[6])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[6]);
        }else if (retailerName.equals(AppSettings.getInstance().getStoreList()[7])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[7]);
        }else if (retailerName.equals(AppSettings.getInstance().getStoreList()[8])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[8]);
        }else if (retailerName.equals(AppSettings.getInstance().getStoreList()[9])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[9]);
        }else if (retailerName.equals(AppSettings.getInstance().getStoreList()[10])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[10]);
        }else if (retailerName.equals(AppSettings.getInstance().getStoreList()[11])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[11]);
        }else if (retailerName.equals(AppSettings.getInstance().getStoreList()[12])){
            return context.getResources().getDrawable(AppSettings.getInstance().getStoreListThumbnailsID()[12]);
        }else {
            return null;
        }
    }

}
