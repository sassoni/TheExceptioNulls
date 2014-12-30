package com.theexceptionulls.projectskullnbones.Card;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.theexceptionulls.projectskullnbones.AppSettings;
import com.theexceptionulls.projectskullnbones.CardData;
import com.theexceptionulls.projectskullnbones.Constants;
import com.theexceptionulls.projectskullnbones.R;
import com.theexceptionulls.projectskullnbones.homescreen.CardsListManager;
import com.theexceptionulls.projectskullnbones.webservices.*;

import java.util.ArrayList;
import java.util.List;

public class OffersFragment extends Fragment {

    //private LinearLayout offerListParentLayout;
    private String barcode;
    private String retailer;
    private int gridPosition;
    private boolean fromRegistration = false;
    private boolean shouldFetchOffers;
    private List<Offers> offersList;
    //private RegisterBroadcastReceiver registerBroadcastReceiver;

    private GridView gridView;
    private ProgressBar progressBar;
    private TextView errorMessage;

    private enum UIOptions {
        EMPTY_OFFERS, LOADING_OFFERS, OFFERS_AVAILABLE;
    }

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
            shouldFetchOffers = false;
            barcode = getArguments().getString(CardData.CARD_NUMBER);
            //retailer = getArguments().getString(CardData.RETAILER_NAME);

        } else {
            fromRegistration = false;
            shouldFetchOffers = true;
            gridPosition = getArguments().getInt(Constants.LOYALTY_CARD_POSITION);
            CardData cardData = CardsListManager.getInstance().getCardDataAtIndex(gridPosition);
            barcode = cardData.getCardNumber();
            //retailer = cardData.getRetailerName();
        }

        //registerBroadcastReceiver = new RegisterBroadcastReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offers_fragment_grid, container, false);

        //offerListParentLayout = (LinearLayout) view.findViewById(R.id.offers_linear_layout);
        gridView = (GridView) view.findViewById(R.id.offer_fragment_gridview);
        progressBar = (ProgressBar) view.findViewById(R.id.offers_fragment_grid_progress_bar);
        errorMessage = (TextView) view.findViewById(R.id.offers_fragment_grid_error_message);

        LoadOffers loadOffers = new LoadOffers(getActivity());
        loadOffers.execute();

        return view;
    }

    private class LoadOffers extends AsyncTask<Void, Void, Void>{

        private Context context;

        public LoadOffers(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setUpScreen(UIOptions.LOADING_OFFERS);
            offersList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {

            for (int i = 1; i<= 16 ;i++){
                Offers offers = new Offers();
                offers.setDescription("Save $" + i);
                offers.setId(i);
                offersList.add(offers);
            }

            OffersGridAdapter offersGridAdapter = new OffersGridAdapter(context, offersList);
            gridView.setAdapter(offersGridAdapter);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setUpScreen(UIOptions.OFFERS_AVAILABLE);
        }
    }

    private void setUpScreen(UIOptions uiOptions){
        switch (uiOptions){
            case EMPTY_OFFERS:
                                gridView.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                errorMessage.setVisibility(View.VISIBLE);
                                break;

            case OFFERS_AVAILABLE:
                                gridView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                errorMessage.setVisibility(View.VISIBLE);
                                break;

            case LOADING_OFFERS:
                                gridView.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                                errorMessage.setVisibility(View.GONE);
                                break;
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
////        if (shouldFetchOffers){
////            GetOffers getOffers = new GetOffers(getActivity().getApplicationContext(), new Handler(this), barcode, AppSettings.getRetailerOpco(retailer));
////            getOffers.execute();
////        }
//
//        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(registerBroadcastReceiver, new IntentFilter(Constants.REGISTRATION_BROADCAST_INTENT));
//
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(registerBroadcastReceiver);
//    }

//    private void addOfferToParent(List<Offers> offersList) {
//
//        if (offersList == null){
//            return;
//        }
//
//        offerListParentLayout.removeAllViews();
//        offerListParentLayout.invalidate();
//
//        for (int i = 0; i< offersList.size(); i++){
//
//            Offers offers = offersList.get(i);
//
//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity().getApplicationContext());
//            View view = layoutInflater.inflate(R.layout.offer_layout, null, false);
//            view.setTag(R.id.offerId, i);
//
//            ImageView imageView = (ImageView) view.findViewById(R.id.offer_layout_offer_image);
//            imageView.setBackground(getResources().getDrawable(R.drawable.offers_image_bkg));
//
//            TextView offerHeading = (TextView) view.findViewById(R.id.offer_layout_offer_heading);
//            offerHeading.setText(offers.getHeading());
//
//            TextView offerDescription = (TextView) view.findViewById(R.id.offer_layout_offer_description);
//            offerDescription.setText(offers.getDescription());
//
//            TextView offerExpiration = (TextView) view.findViewById(R.id.offer_layout_offer_expiration);
//            offerExpiration.setText(offers.getExpiration());
//
//            offerListParentLayout.addView(view);
//            offerListParentLayout.invalidate();
//            OfferImageLoader.getInstance().downloadImage(i,Constants.WEB_SERVICE_URL+offers.getImageUrl(),new Handler(this),1);
//        }
//
//    }

//    @Override
//    public boolean handleMessage(Message msg) {
//
//        if (msg.what == BaseWebService.WEB_SERVICE_SUCCESS){
//
//            if (msg.obj instanceof GetCouponOffersResponse){
//
//                GetCouponOffersResponse getCouponOffersResponse = (GetCouponOffersResponse) msg.obj;
//                if (offersList != null){
//                    offersList.clear();
//                }
//
//                offersList = getCouponOffersResponse.getOffersList();
//                addOfferToParent(offersList);
//            }
//
//        }else {
//            Toast.makeText(getActivity().getApplicationContext(), "Error fetching offers", Toast.LENGTH_LONG).show();
//        }
//
//        if (msg.what == OfferImageLoader.IMAGE_CALLBACK){
//            final int offerIndex = msg.arg1;
//
//            Bitmap bitmap = (Bitmap) msg.obj;
//            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
//
//            View view = offerListParentLayout.getChildAt(offerIndex);
//            ImageView imageView = (ImageView) view.findViewById(R.id.offer_layout_offer_image);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                imageView.setBackground(bitmapDrawable);
//            } else {
//                imageView.setBackgroundDrawable(bitmapDrawable);
//            }
//
//        }
//
//        return false;
//    }

//    private class RegisterBroadcastReceiver extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            shouldFetchOffers = true;
//            GetOffers getOffers = new GetOffers(getActivity().getApplicationContext(), new Handler(OffersFragment.this), barcode, AppSettings.getRetailerOpco(retailer));
//            getOffers.execute();
//            Toast.makeText(getActivity().getApplicationContext(), "Received registration event", Toast.LENGTH_LONG).show();
//        }
//    }

}
