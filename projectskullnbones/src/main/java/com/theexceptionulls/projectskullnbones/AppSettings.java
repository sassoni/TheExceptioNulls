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

    private AppSettings(){

    }

    public static AppSettings getInstance(){
        if (instance == null){
            instance = new AppSettings();
        }
        return instance;
    }

    public void loadOffersList(String jsonData){
        offersList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i =0; i<jsonArray.length(); i++){
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

    public List<Offers> getRandomOffers(int size){

        List<Offers> randomOfferList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();

        Random random = new Random();

        for (int i = 0;i<size; i++){
            int randomInt;
            do {
                randomInt = random.nextInt();
                if (randomInt < 0){
                    randomInt = randomInt*-1;
                }

                randomInt = randomInt%16;
            }while (integerList.contains(randomInt) || randomInt ==0);
            randomOfferList.add(offersList.get(randomInt));
            integerList.add(randomInt);
        }

        Log.i("APPSETTINGS", "offers out!!!");
        for (int i=0; i<randomOfferList.size(); i++) {
            Log.i("APPSETTINGS", "id: " + randomOfferList.get(i).getId());
        }

        return randomOfferList;
    }

    public List<Offers> getNewRandomOffers(int size, List<Integer> existingOffers){
        List<Integer> usedIds = new ArrayList<>();
        List<Offers> randomOfferList = new ArrayList<>();

        Random random = new Random();

        for (Integer i: existingOffers) {
            Log.i("APPSETTINGS", "existing: " + i);
        }

        while (randomOfferList.size() < size) {
            int randInt = random.nextInt(18);
            Log.i("APPSETTINGS", "randInt: " + randInt);
            if (!usedIds.contains(randInt) && !existingOffers.contains(randInt)){
                Log.i("APPSETTINGS", "adding");
                randomOfferList.add(offersList.get(randInt));
                Log.i("APPSETTINGS", "Getting offer: " + randInt + " with id: " + offersList.get(randInt).getId());
                usedIds.add(randInt);
            } else {
                Log.i("APPSETTINGS", "not adding");
            }
        }

        return randomOfferList;
    }

    public static int[] getStoreListThumbnailsID() {
        return storeListThumbnailsID;
    }

    public static Drawable getDrawable(Context context, int retailerId){

        Drawable drawable = null;

        switch (retailerId){
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
