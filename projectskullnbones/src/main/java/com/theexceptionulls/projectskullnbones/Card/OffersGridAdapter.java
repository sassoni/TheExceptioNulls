package com.theexceptionulls.projectskullnbones.Card;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.theexceptionulls.projectskullnbones.R;

/**
 * Created by Avatapally on 12/27/14.
 */
public class OffersGridAdapter extends BaseAdapter {

    private Context context;

    public OffersGridAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.offer_layout_grid, null, false);

            holder = new ViewHolder();
            holder.offerImage = (ImageView) convertView.findViewById(R.id.offer_layout_offer_image);
            holder.offerClip = (Button) convertView.findViewById(R.id.offer_clip);
            holder.offerDislike = (Button) convertView.findViewById(R.id.offer_dislike);
            holder.offerDescription = (TextView) convertView.findViewById(R.id.offer_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.offerClip.setTag(position);
        holder.offerClip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.offerDislike.setTag(position);
        holder.offerDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView offerImage;
        Button offerDislike;
        Button offerClip;
        TextView offerDescription;
    }
}
