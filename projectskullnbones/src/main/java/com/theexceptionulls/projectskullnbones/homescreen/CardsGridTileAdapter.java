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
import com.theexceptionulls.projectskullnbones.R;

public class CardsGridTileAdapter extends BaseAdapter {

    private Context context;

    private String[] stores = {
            "A&P",
            "Family Express",
            "Giant"
    };

    private int[] storeImages = {
            R.drawable.i_aandp,
            R.drawable.i_familyexpress,
            R.drawable.i_gl,
    };

    public CardsGridTileAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return stores.length;
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

        Drawable drawable = context.getResources().getDrawable(storeImages[position]);

        holder.image.setImageDrawable(drawable);
        holder.text.setText(stores[position]);

        return convertView;
    }

}
