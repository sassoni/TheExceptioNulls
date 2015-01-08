package com.theexceptionulls.projectskullnbones;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.theexceptionulls.projectskullnbones.webservices.Offers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AppSettings {

    private List<Offers> offersList;
    private static int[] storeListThumbnailsID = {
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

    private static AppSettings instance;

    private AppSettings() {

    }

    public static AppSettings getInstance() {
        if (instance == null) {
            instance = new AppSettings();
        }
        return instance;
    }

    public void loadOffersList(String jsonData) {
        offersList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Offers offers = new Offers();
                offers.setDescription(jsonObject.getString("description"));
                offers.setExpiration(jsonObject.getString("expiration"));
                offers.setHeading(jsonObject.getString("heading"));
                offers.setId(jsonObject.getInt("offerId"));
                offersList.add(offers);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Offers> getRandomOffers(int size) {
        return getRandomOffers(size, new ArrayList<Integer>()) ;
    }

    public List<Offers> getRandomOffers(int size, List<Integer> idsToExclude) {
        List<Integer> usedIds = new ArrayList<>();
        List<Offers> randomOfferList = new ArrayList<>();

        Random random = new Random();

        while (randomOfferList.size() < size) {
            int randInt = random.nextInt(18);
            if (!usedIds.contains(randInt) && !idsToExclude.contains(randInt)) {
                randomOfferList.add(offersList.get(randInt));
                usedIds.add(randInt);
            }
        }

        return randomOfferList;
    }

    public static int[] getStoreListThumbnailsID() {
        return storeListThumbnailsID;
    }

    public static Drawable getDrawable(Context context, int retailerId) {

        Drawable drawable = null;

        switch (retailerId) {
            case 0:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[0]);
                break;
            case 1:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[1]);
                break;
            case 2:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[2]);
                break;
            case 3:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[3]);
                break;
            case 4:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[4]);
                break;
            case 5:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[5]);
                break;
            case 6:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[6]);
                break;
            case 7:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[7]);
                break;
            case 8:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[8]);
                break;
            case 9:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[9]);
                break;
            case 10:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[10]);
                break;
            case 11:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[11]);
                break;
            case 12:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[12]);
                break;
            default:
                drawable = context.getResources().getDrawable(getStoreListThumbnailsID()[0]);
                break;
        }
        return drawable;
    }

}
