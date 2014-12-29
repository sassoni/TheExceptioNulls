package com.theexceptionulls.projectskullnbones.Card;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.theexceptionulls.projectskullnbones.R;
import com.theexceptionulls.projectskullnbones.webservices.Offers;

import java.util.List;

/**
 * Created by Avatapally on 12/27/14.
 */
public class OffersGridAdapter extends BaseAdapter {

    private Context context;
    private List<Offers> offerList;

    public OffersGridAdapter(Context context, List<Offers> offersList){
        this.context = context;
        this.offerList = offersList;
    }

    @Override
    public int getCount() {
        return offerList.size();
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

        Offers offers = offerList.get(position);

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

        holder.offerDescription.setText("Save $1.50");

        Drawable drawable = null;
        switch (offers.getId()){

            case 1:
                drawable = context.getResources().getDrawable(R.drawable.i_allnaturaleggs);
                break;
            case 2:
                drawable = context.getResources().getDrawable(R.drawable.i_breakstones);
                break;
            case 3:
                drawable = context.getResources().getDrawable(R.drawable.i_cheerios);
                break;
            case 4:
                drawable = context.getResources().getDrawable(R.drawable.i_chichi);
                break;
            case 5:
                drawable = context.getResources().getDrawable(R.drawable.i_chobani);
                break;
            case 6:
                drawable = context.getResources().getDrawable(R.drawable.i_floridasnaturals);
                break;
            case 7:
                drawable = context.getResources().getDrawable(R.drawable.i_generalmills);
                break;
            case 8:
                drawable = context.getResources().getDrawable(R.drawable.i_gerberorganicfood);
                break;
            case 9:
                drawable = context.getResources().getDrawable(R.drawable.i_gladeexpressions);
                break;
            case 10:
                drawable = context.getResources().getDrawable(R.drawable.i_highperformancedetergent);
                break;
            case 11:
                drawable = context.getResources().getDrawable(R.drawable.i_lobsterseafood);
                break;
            case 12:
                drawable = context.getResources().getDrawable(R.drawable.i_macandcheese);
                break;
            case 13:
                drawable = context.getResources().getDrawable(R.drawable.i_minutemaidlemonade);
                break;
            case 14:
                drawable = context.getResources().getDrawable(R.drawable.i_peperagefarms);
                break;
            case 15:
                drawable = context.getResources().getDrawable(R.drawable.i_perduechicken);
                break;
            case 16:
                drawable = context.getResources().getDrawable(R.drawable.i_puffs);
                break;
            default:
                drawable = context.getResources().getDrawable(R.drawable.i_puffs);
                break;
        }
        holder.offerImage.setImageDrawable(drawable);

        return convertView;
    }

    private class ViewHolder {
        ImageView offerImage;
        Button offerDislike;
        Button offerClip;
        TextView offerDescription;
    }
}
