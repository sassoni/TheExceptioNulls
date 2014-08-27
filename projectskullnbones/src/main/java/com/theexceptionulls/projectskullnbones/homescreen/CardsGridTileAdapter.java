package com.theexceptionulls.projectskullnbones.homescreen;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.theexceptionulls.projectskullnbones.R;
import org.w3c.dom.Text;

/**
 * Created by cchaleva on 8/27/2014.
 */
public class CardsGridTileAdapter extends BaseAdapter {

    private Context context;
    private Resources resources;
    private DisplayMetrics metrics;

    private String[] storeList = {
            "A&P",
            "Family Express",
            "Giant",
            "Giant BONUSCARD",
            "Harris Teeter",
            "Kroger",
            "Marsh",
            "Martin's",
            "Safeway",
            "ShopRite",
            "Stop & Shop",
            "Walmart",
            "Weis"
    };

    private int[] storeListThumbnailsID = {
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

    public CardsGridTileAdapter(Context context) {
        this.context = context;
        resources = context.getResources();
        metrics = resources.getDisplayMetrics();
    }

    @Override
    public int getCount() {
        return storeListThumbnailsID.length;
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

        TextView textView;
        View view;

        if (convertView == null) {
//            textView = new TextView(context);
//            textView.setPadding(dpToPx(20), dpToPx(20), dpToPx(20), 0);
//            textView.setWidth(dpToPx(150));
//            textView.setHeight(dpToPx(300));
//            textView.setBackgroundColor(Color.parseColor("#CCCCCC"));
//            textView.setGravity(Gravity.CENTER);
//            textView.setTextSize(26);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_tile, null, false);
        } else {
            // textView = (TextView) convertView;
            view = convertView;
        }

//        textView.setCompoundDrawablesWithIntrinsicBounds(0, thumbnailsIds[position], 0, 0);
//        textView.setText("George\nClooney");

//        return textView;
        return view;

    }

    private int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }


//        ImageView imageView;
//        if (convertView == null) {
//            imageView = new ImageView(context);
//            imageView.setPadding(40, 40, 40, 80);
//            imageView.setLayoutParams(new GridView.LayoutParams(300, 400));
//            imageView.setScaleType(ImageView.ScaleType.FIT_START);
//            imageView.setBackgroundColor(Color.parseColor("#CCCCCC"));
//            //imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//        imageView.setImageResource(thumbnailsIds[position]);
//        return imageView;
//    }

}
