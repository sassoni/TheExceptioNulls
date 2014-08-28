package com.theexceptionulls.projectskullnbones;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohithavatapally on 8/27/14.
 */
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

    public static AppSettings getInstance(){
        if(instance == null){
            return instance = new AppSettings();
        }else {
            return instance;
        }
    }

    public String[] getStoreList() {
        return storeList;
    }

    public int[] getStoreListThumbnailsID() {
        return storeListThumbnailsID;
    }

    public List<CardData> getCardDataList(){
        return cardDataList;
    }

}
