package com.theexceptionulls.projectskullnbones.Card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.theexceptionulls.projectskullnbones.CardData;
import com.theexceptionulls.projectskullnbones.Constants;
import com.theexceptionulls.projectskullnbones.R;

public class SavingsFragment extends Fragment {

    String barcode;
    String retailer;
    int gridPosition;
    static boolean fromRegistration = false;

    public SavingsFragment() {
    }

    public static final SavingsFragment newInstance(Bundle dataBundle) {
        SavingsFragment fragment = new SavingsFragment();

        Bundle bundle = new Bundle();

        String intentFrom = dataBundle.getString(Constants.INTENT_FROM);
        bundle.putString(Constants.INTENT_FROM, dataBundle.getString(Constants.INTENT_FROM));

        if (intentFrom.equals(Constants.INTENT_FROM_REGISTRATION)) {
            bundle.putString(CardData.CARD_NUMBER, dataBundle.getString(CardData.CARD_NUMBER));
            bundle.putString(CardData.RETAILER_NAME, dataBundle.getString(CardData.RETAILER_NAME));
        } else {
            bundle.putInt(Constants.LOYALTY_CARD_POSITION, dataBundle.getInt(Constants.LOYALTY_CARD_POSITION));
        }

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
            gridPosition = getArguments().getInt(Constants.LOYALTY_CARD_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.savings_fragment, container, false);
        return view;
    }
}
