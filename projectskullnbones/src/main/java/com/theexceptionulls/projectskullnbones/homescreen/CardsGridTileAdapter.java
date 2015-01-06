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
    private boolean isEditing;
    private String[] retailerNames;

    public CardsGridTileAdapter(Context context) {
        this.context = context;
        isEditing = false;
        retailerNames = context.getResources().getStringArray(R.array.retailer_names);
    }

    @Override
    public int getCount() {
        return CardsListManager.getInstance().getCardDataListSize();
    }

    @Override
    public Object getItem(int position) {
        return CardsListManager.getInstance().getCardDataAtIndex(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ImageView image;
        ImageView remove;
        TextView text;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_tile, null, false);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.grid_tile_text);
            holder.image = (ImageView) convertView.findViewById(R.id.grid_tile_image);
            holder.remove = (ImageView) convertView.findViewById(R.id.grid_tile_remove);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CardData cardData = CardsListManager.getInstance().getCardDataAtIndex(position);
        Drawable drawable = AppSettings.getDrawable(context, cardData.getRetailerId());
        holder.image.setImageDrawable(drawable);
        holder.text.setText(retailerNames[cardData.getRetailerId()]);
        holder.remove.setTag(position);

        if (isEditing){
            holder.remove.setVisibility(View.VISIBLE);
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cardPosition = (Integer) v.getTag();
                    CardsListManager.getInstance().removeCardDataAtIndex(cardPosition);
                    CardsListManager.getInstance().saveList(context);
                    notifyDataSetChanged();
                }
            });
        }else {
            holder.remove.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    public void startEditing(){
        if (isEditing)
            return;
        isEditing = true;
        notifyDataSetChanged();
    }

    public void stopEditing(){
        if (!isEditing)
            return;
        isEditing = false;
        notifyDataSetChanged();
    }

}
