package com.theexceptionulls.projectskullnbones.Card;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.theexceptionulls.projectskullnbones.AppSettings;
import com.theexceptionulls.projectskullnbones.Constants;
import com.theexceptionulls.projectskullnbones.R;
import com.theexceptionulls.projectskullnbones.homescreen.CardsListManager;
import com.theexceptionulls.projectskullnbones.webservices.Offers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class OffersFragment extends Fragment implements OffersGridAdapter.OffersGridEventListener {

    private int cardPosition;
    private List<Offers> offersList;
    private String cardNumber;
    private int retailerId;
    private GridView gridView;
    private ProgressBar progressBar;
    private TextView errorMessage;
    private OffersGridAdapter offersGridAdapter;

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
            retailerId = getArguments().getInt(Constants.RETAILER_ID);
            cardNumber = getArguments().getString(Constants.CARD_NUMBER);
            cardPosition = CardsListManager.getInstance().getCardDataListSize() - 1;
        } else {
            cardPosition = getArguments().getInt(Constants.CARD_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offers_fragment_grid, container, false);
        gridView = (GridView) view.findViewById(R.id.offer_fragment_gridview);
        progressBar = (ProgressBar) view.findViewById(R.id.offers_fragment_grid_progress_bar);
        errorMessage = (TextView) view.findViewById(R.id.offers_fragment_grid_error_message);

        LoadOffers loadOffers = new LoadOffers(getActivity());
        loadOffers.execute();

        return view;
    }

    private class LoadOffers extends AsyncTask<Void, Void, Void> {

        private Context context;

        public LoadOffers(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setUpScreen(UIOptions.LOADING_OFFERS);
        }

        @Override
        protected Void doInBackground(Void... params) {

            loadOffersList(getActivity());
            if (offersList == null) {
                offersList = AppSettings.getInstance().getInitialOffers(Constants.OFFERS_LIST_SIZE);
                saveOffersList(getActivity());
            }

            offersGridAdapter = new OffersGridAdapter(context, offersList, OffersFragment.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setUpScreen(UIOptions.OFFERS_AVAILABLE);
            gridView.setAdapter(offersGridAdapter);
        }

    }

    private void loadOffersList(Context context) {
        try {
            String fileName = Constants.OFFERS_FILE_PREFIX + cardPosition;
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            offersList = (List<Offers>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private synchronized void saveOffersList(Context context) {
        if (offersList != null) {
            try {
                String fileName = Constants.OFFERS_FILE_PREFIX + cardPosition;
                FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(offersList);
                fileOutputStream.close();
                objectOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpScreen(UIOptions uiOptions) {
        switch (uiOptions) {
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

    @Override
    public void offerClipped() {
        saveOffersList(getActivity());
    }

    @Override
    public void offerLiked() {
        saveOffersList(getActivity());
    }

    @Override
    public void offerDisliked(int position, boolean dislikedWhenClicked) {
        saveOffersList(getActivity());
        if (!dislikedWhenClicked) {
            showDislikeDialog(position);
        }
    }

    public boolean areAnyOffersClipped() {
        for (Offers offer: offersList) {
            if (offer.isClipped()) {
                return true;
            }
        }
        return false;
    }

    public void deleteClippedOffers(Context context) {
        for (int i = 0; i < offersList.size(); i++) {
            if (offersList.get(i).isClipped()) {
                offersList.remove(i);
            }
        }
        saveOffersList(context);
    }

    public void showDislikeDialog(final int offerPosition) {
        new AlertDialog.Builder(this.getActivity())
                .setTitle(R.string.dislike_feedback)
                .setSingleChoiceItems(R.array.dislike_excuses, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // position is which
                    }
                })
                .setPositiveButton(R.string.dislike_keep_offer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dislike_delete_offer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //offersList = AppSettings.getInstance().getRandomOffers(Constants.OFFERS_LIST_SIZE);
                        for (Offers offer : offersList) {
                            Log.i("OFERSGRIDADAPTER", "id chosen: " + offer.getId());
                        }
                        Log.i("OFERSGRIDADAPTER", "position to remove: " + offerPosition);
                        offersList.remove(offerPosition);
//                        offersGridAdapter.notifyDataSetChanged();
                        offersGridAdapter.updateAdapter(offersList);
                        saveOffersList(getActivity());
                        dialog.dismiss();
                    }
                })
                .show();
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
