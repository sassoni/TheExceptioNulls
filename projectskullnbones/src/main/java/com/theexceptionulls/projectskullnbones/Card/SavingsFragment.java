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

    private String barcode;
    private String retailer;
    private int gridPosition;
    private static boolean fromRegistration = false;

    public SavingsFragment() {
    }

    public static final SavingsFragment newInstance(Bundle bundle) {
        SavingsFragment fragment = new SavingsFragment();
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
            //retailer = getArguments().getString(CardData.RETAILER_NAME);
        } else {
            fromRegistration = false;
            gridPosition = getArguments().getInt(Constants.LOYALTY_CARD_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.savings_fragment, container, false);
        return view;
    }
}
