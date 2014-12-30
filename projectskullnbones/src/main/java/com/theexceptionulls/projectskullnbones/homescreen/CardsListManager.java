package com.theexceptionulls.projectskullnbones.homescreen;

import android.content.Context;
import android.net.Uri;

import com.theexceptionulls.projectskullnbones.CardData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravatapa on 12/22/2014.
 */
public class CardsListManager {

    private static CardsListManager instance;
    private List<CardData> cardDataList;

    public static CardsListManager getInstance(){
        if (instance == null){
            instance = new CardsListManager();
        }
        return instance;
    }

    public void loadCardsList(Context context){
        try {
            FileInputStream fileInputStream = context.openFileInput(CardData.OUTPUT_FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            cardDataList = (List<CardData>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void saveList(Context context) {

        if (cardDataList == null || cardDataList.size() == 0){
            return;
        }

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(CardData.OUTPUT_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(cardDataList);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CardData getCardDataAtIndex(int index){
        if (cardDataList == null || cardDataList.size() == 0){
            return null;
        }
        return cardDataList.get(index);
    }

    public void addNewCard(CardData cardData){
        if (cardData == null){
            return;
        }

        if (cardDataList == null){
            cardDataList = new ArrayList<>();
        }
        cardDataList.add(cardData);
    }

    public int getCardDataListSize(){
        if (cardDataList == null){
            return 0;
        }
        return cardDataList.size();
    }

    public void removeCardDataAtIndex(int index){
        if (cardDataList == null || cardDataList.size() == 0){
            return;
        }
        cardDataList.remove(index);
    }

    public void setPhotoUriInCardDataWithName(int cardIndex, Uri uri) {
        cardDataList.get(cardIndex).setPhotoUri(uri.toString());
    }

}
