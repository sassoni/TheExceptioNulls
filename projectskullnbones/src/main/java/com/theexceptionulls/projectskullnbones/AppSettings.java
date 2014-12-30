package com.theexceptionulls.projectskullnbones;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class AppSettings {

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

    public static int[] getStoreListThumbnailsID() {
        return storeListThumbnailsID;
    }

    public static Drawable getDrawable(Context context, int retailerId){
        
        Drawable drawable = null;
        
        switch (retailerId){
            case 0:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[0]);
                break;
            case 1:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[1]);
                break;
            case 2:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[2]);
                break;
            case 3:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[3]);
                break;
            case 4:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[4]);
                break;
            case 5:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[5]);
                break;
            case 6:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[6]);
                break;
            case 7:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[7]);
                break;
            case 8:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[8]);
                break;
            case 9:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[9]);
                break;
            case 10:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[10]);
                break;
            case 11:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[11]);
                break;
            case 12:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[12]);
                break;
            default:
                drawable = context.getResources().getDrawable(AppSettings.getStoreListThumbnailsID()[0]);
                break;
        }
        return drawable;
    }

}
