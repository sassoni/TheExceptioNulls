package com.theexceptionulls.projectskullnbones.homescreen;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.theexceptionulls.projectskullnbones.AppSettings;
import com.theexceptionulls.projectskullnbones.CardData;
import com.theexceptionulls.projectskullnbones.R;

public class CardsGridTileAdapter extends BaseAdapter {

    private Context context;

//    private String[] stores = {
//            "A&P",
//            "Family Express",
//            "Giant"
//    };
//
//    private int[] storeImages = {
//            R.drawable.i_aandp,
//            R.drawable.i_familyexpress,
//            R.drawable.i_gl,
//    };

    public CardsGridTileAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return AppSettings.getInstance().getCardDataList().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ImageView image;
        TextView text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_tile, null, false);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.grid_tile_text);
            holder.image = (ImageView) convertView.findViewById(R.id.grid_tile_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final CardData cardData = AppSettings.getInstance().getCardDataList().get(position);
        Drawable drawable = getDrawable(cardData.getRetailerName());
        holder.image.setImageDrawable(drawable);
        holder.text.setText(cardData.getRetailerName());

        return convertView;
    }

    private Drawable getDrawable(String retailerName){
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
