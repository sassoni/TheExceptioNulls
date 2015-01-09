package com.theexceptionulls.projectskullnbones.Card;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
    private Resources resources;

    public interface OffersGridEventListener {
        public void offerClipped();

        public void offerLiked();

        public void offerDisliked(int position, boolean dislikedWhenClicked);
    }

    private OffersGridEventListener offersGridEventListener;

    public OffersGridAdapter(Context context, List<Offers> offersList, OffersGridEventListener offersGridEventListener) {
        this.context = context;
        this.offerList = offersList;
        resources = context.getResources();
        this.offersGridEventListener = offersGridEventListener;
    }

    public void updateAdapter(List<Offers> offerList) {
        this.offerList = offerList;
        notifyDataSetChanged();
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
            holder.offerLike = (Button) convertView.findViewById(R.id.offer_like);
            holder.offerDescription = (TextView) convertView.findViewById(R.id.offer_description);
            holder.offerSaving = (TextView) convertView.findViewById(R.id.offer_savings);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Offers offers = offerList.get(position);

        holder.offerClip.setTag(position);
        setClippedButtonState(holder.offerClip, offers.isClipped());

        holder.offerClip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                Offers offersData = offerList.get(position);
                if (offersData.isClipped()) {
                    offersData.setClipped(false);
                    setClippedButtonState(holder.offerClip, false);
                } else {
                    offersData.setClipped(true);
                    setClippedButtonState(holder.offerClip, true);
                }
                offersGridEventListener.offerClipped();
            }
        });

        holder.offerDislike.setTag(position);
        Offers offersData = offerList.get(position);
        if (offersData.isDisliked()) {
            holder.offerDislike.setBackgroundResource(R.drawable.i_dislike_);
        } else {
            holder.offerDislike.setBackgroundResource(R.drawable.i_dislike);
        }
        holder.offerDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                Offers offersData = offerList.get(position);
                if (offersData.isDisliked()) {  // un-dislike the offer
                    holder.offerDislike.setBackgroundResource(R.drawable.i_dislike);
                    offersData.setDisliked(false);

                    offersGridEventListener.offerDisliked(position, true);
                } else {  // dislike the offer
                    holder.offerDislike.setBackgroundResource(R.drawable.i_dislike_);
                    offersData.setDisliked(true);

                    holder.offerLike.setBackgroundResource(R.drawable.i_like);
                    offersData.setLiked(false);

                    offersGridEventListener.offerDisliked(position, false);
                }
            }
        });

        holder.offerLike.setTag(position);
        if (offersData.isLiked()) {
            holder.offerLike.setBackgroundResource(R.drawable.i_like_);
        } else {
            holder.offerLike.setBackgroundResource(R.drawable.i_like);
        }
        holder.offerLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                Offers offersData = offerList.get(position);
                if (offersData.isLiked()) { // un-like the offer
                    holder.offerLike.setBackgroundResource(R.drawable.i_like);
                    offersData.setLiked(false);
                } else {  // like the offer
                    holder.offerLike.setBackgroundResource(R.drawable.i_like_);
                    offersData.setLiked(true);

                    holder.offerDislike.setBackgroundResource(R.drawable.i_dislike);
                    offersData.setDisliked(false);
                }
                offersGridEventListener.offerLiked();
            }
        });

        holder.offerDescription.setText(offers.getDescription());
        holder.offerSaving.setText(offers.getHeading());

        Drawable drawable = null;
        switch (offers.getId()) {
            case 0:
                drawable = context.getResources().getDrawable(R.drawable.i_allnaturaleggs);
                break;
            case 1:
                drawable = context.getResources().getDrawable(R.drawable.i_breakstones);
                break;
            case 2:
                drawable = context.getResources().getDrawable(R.drawable.i_cheerios);
                break;
            case 3:
                drawable = context.getResources().getDrawable(R.drawable.i_chichi);
                break;
            case 4:
                drawable = context.getResources().getDrawable(R.drawable.i_chobani);
                break;
            case 5:
                drawable = context.getResources().getDrawable(R.drawable.i_floridasnaturals);
                break;
            case 6:
                drawable = context.getResources().getDrawable(R.drawable.i_generalmills);
                break;
            case 7:
                drawable = context.getResources().getDrawable(R.drawable.i_gerberorganicfood);
                break;
            case 8:
                drawable = context.getResources().getDrawable(R.drawable.i_gladeexpressions);
                break;
            case 9:
                drawable = context.getResources().getDrawable(R.drawable.i_highperformancedetergent);
                break;
            case 10:
                drawable = context.getResources().getDrawable(R.drawable.i_lobsterseafood);
                break;
            case 11:
                drawable = context.getResources().getDrawable(R.drawable.i_macandcheese);
                break;
            case 12:
                drawable = context.getResources().getDrawable(R.drawable.i_minutemaidlemonade);
                break;
            case 13:
                drawable = context.getResources().getDrawable(R.drawable.i_peperagefarms);
                break;
            case 14:
                drawable = context.getResources().getDrawable(R.drawable.i_perduechicken);
                break;
            case 15:
                drawable = context.getResources().getDrawable(R.drawable.i_puffs);
                break;
            case 16:
                drawable = context.getResources().getDrawable(R.drawable.i_scott);
                break;
            case 17:
                drawable = context.getResources().getDrawable(R.drawable.i_sscranberryjuice);
                break;
            case 18:
                drawable = context.getResources().getDrawable(R.drawable.i_specialk);
                break;
            default:
                drawable = context.getResources().getDrawable(R.drawable.i_scott);
                break;
        }
        holder.offerImage.setImageDrawable(drawable);

        return convertView;
    }

    private void setClippedButtonState(Button clippedButton, boolean isClipped) {
        if (isClipped) {
            clippedButton.setText(resources.getString(R.string.offer_clip_clipped));
            clippedButton.setTextColor(resources.getColor(R.color.offer_clipped_state));
            clippedButton.setCompoundDrawablesWithIntrinsicBounds(null, null, resources.getDrawable(R.drawable.i_saved), null);
        } else {
            clippedButton.setText(resources.getString(R.string.offer_clip_unclipped));
            clippedButton.setTextColor(resources.getColor(R.color.offer_unclipped_state));
            clippedButton.setCompoundDrawablesWithIntrinsicBounds(null, null, resources.getDrawable(R.drawable.i_addcoupon), null);
        }
    }

    private class ViewHolder {
        ImageView offerImage;
        Button offerDislike;
        Button offerLike;
        Button offerClip;
        TextView offerDescription;
        TextView offerSaving;
    }
}
