package com.theexceptionulls.projectskullnbones.addcard;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.theexceptionulls.projectskullnbones.R;

public class StoreListAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] stores;
    private int[] storeImages;

    public StoreListAdapter(Context context, String[] stores, int[] storeImages) {
        super(context, R.layout.store_list_row, stores);
        this.context = context;
        this.stores = stores;
        this.storeImages = storeImages;
    }

    private class ViewHolder {
        ImageView image;
        TextView text;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.store_list_row, null, true);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.store_name);
            holder.image = (ImageView) convertView.findViewById(R.id.store_thumbnail);
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
