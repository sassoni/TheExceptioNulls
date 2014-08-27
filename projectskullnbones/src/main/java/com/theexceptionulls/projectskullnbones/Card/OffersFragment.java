package com.theexceptionulls.projectskullnbones.Card;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.theexceptionulls.projectskullnbones.R;

/**
 * Created by rohithavatapally on 8/27/14.
 */
public class OffersFragment extends Fragment {

    private LinearLayout offerListParentLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offers_fragment, container, false);

        offerListParentLayout = (LinearLayout) view.findViewById(R.id.offers_linear_layout);

        for (int i=0; i<7; i++){
            addOfferToParent();
        }

        return view;
    }

    private void addOfferToParent(){

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity().getApplicationContext());
        View view = layoutInflater.inflate(R.layout.offer_layout, null, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.offer_layout_offer_image);
        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        imageView.setBackgroundColor(Color.BLACK);

        TextView offerHeading = (TextView) view.findViewById(R.id.offer_layout_offer_heading);
        offerHeading.setText("Save $1.50");

        TextView offerDescription = (TextView) view.findViewById(R.id.offer_layout_offer_description);
        offerDescription.setText("On any ONE(1) six pack or larger");

        TextView offerExpiration = (TextView) view.findViewById(R.id.offer_layout_offer_expiration);
        offerExpiration.setText("Expires 09/12/2014");

//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        layoutParams.setMargins(10, 30, 10, 30);

        offerListParentLayout.addView(view);
        offerListParentLayout.invalidate();
    }
}
