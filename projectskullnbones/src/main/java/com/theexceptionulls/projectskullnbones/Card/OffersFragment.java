package com.theexceptionulls.projectskullnbones.Card;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.theexceptionulls.projectskullnbones.AppSettings;
import com.theexceptionulls.projectskullnbones.CardData;
import com.theexceptionulls.projectskullnbones.Constants;
import com.theexceptionulls.projectskullnbones.R;
import com.theexceptionulls.projectskullnbones.webservices.*;

import java.util.List;

public class OffersFragment extends Fragment implements Handler.Callback {

    private LinearLayout offerListParentLayout;
    private String barcode;
    private String retailer;
    private int gridPosition;
    private static boolean fromRegistration = false;
    private List<Offers> offersList;

    public OffersFragment() {
    }

    public static final OffersFragment newInstance(Bundle bundle) {
        OffersFragment fragment = new OffersFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String intentFrom = getArguments().getString(Constants.INTENT_FROM);

        if (intentFrom.equals(Constants.INTENT_FROM_REGISTRATION)) {
            fromRegistration = true;
            barcode = getArguments().getString(CardData.CARD_NUMBER);
            retailer = getArguments().getString(CardData.RETAILER_NAME);

        } else {
            fromRegistration = false;
            gridPosition = getArguments().getInt(Constants.LOYALTY_CARD_POSITION);
            barcode = AppSettings.getInstance().getCardDataList().get(gridPosition).getCardNumber();
            retailer = AppSettings.getInstance().getCardDataList().get(gridPosition).getRetailerName();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offers_fragment, container, false);

        offerListParentLayout = (LinearLayout) view.findViewById(R.id.offers_linear_layout);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetOffers getOffers = new GetOffers(getActivity().getApplicationContext(), new Handler(this), barcode, AppSettings.getRetailerOpco(retailer));
        getOffers.execute();
    }

    private void addOfferToParent(List<Offers> offersList) {

        if (offersList == null){
            return;
        }

        offerListParentLayout.removeAllViews();
        offerListParentLayout.invalidate();

        for (int i = 0; i< offersList.size(); i++){

            Offers offers = offersList.get(i);

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity().getApplicationContext());
            View view = layoutInflater.inflate(R.layout.offer_layout, null, false);
            view.setTag(R.id.offerId, i);

            ImageView imageView = (ImageView) view.findViewById(R.id.offer_layout_offer_image);
            imageView.setBackground(getResources().getDrawable(R.drawable.offers_image_bkg));

            TextView offerHeading = (TextView) view.findViewById(R.id.offer_layout_offer_heading);
            offerHeading.setText(offers.getHeading());

            TextView offerDescription = (TextView) view.findViewById(R.id.offer_layout_offer_description);
            offerDescription.setText(offers.getDescription());

            TextView offerExpiration = (TextView) view.findViewById(R.id.offer_layout_offer_expiration);
            offerExpiration.setText(offers.getExpiration());

            offerListParentLayout.addView(view);
            offerListParentLayout.invalidate();
            OfferImageLoader.getInstance().downloadImage(i,Constants.WEB_SERVICE_URL+offers.getImageUrl(),new Handler(this),1);
        }

    }

    @Override
    public boolean handleMessage(Message msg) {

        if (msg.what == BaseWebService.WEB_SERVICE_SUCCESS){

            if (msg.obj instanceof GetCouponOffersResponse){

                GetCouponOffersResponse getCouponOffersResponse = (GetCouponOffersResponse) msg.obj;
                if (offersList != null){
                    offersList.clear();
                }

                offersList = getCouponOffersResponse.getOffersList();
                addOfferToParent(offersList);
            }

        }else {
            Toast.makeText(getActivity().getApplicationContext(), "Error fetching offers", Toast.LENGTH_LONG).show();
        }

        if (msg.what == OfferImageLoader.IMAGE_CALLBACK){
            final int offerIndex = msg.arg1;

            Bitmap bitmap = (Bitmap) msg.obj;
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);

            View view = offerListParentLayout.getChildAt(offerIndex);
            ImageView imageView = (ImageView) view.findViewById(R.id.offer_layout_offer_image);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                imageView.setBackground(bitmapDrawable);
            } else {
                imageView.setBackgroundDrawable(bitmapDrawable);
            }

        }

        return false;
    }
}
