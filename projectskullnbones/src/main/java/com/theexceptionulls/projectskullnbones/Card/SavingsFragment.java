package com.theexceptionulls.projectskullnbones.Card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.theexceptionulls.projectskullnbones.CardData;
import com.theexceptionulls.projectskullnbones.R;

public class SavingsFragment extends Fragment {

    String barcode;
    String retailer;

    public SavingsFragment() {
    }

    public static final SavingsFragment newInstance(CardData cardData) {
        SavingsFragment fragment = new SavingsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(CardData.CARD_NUMBER, cardData.getCardNumber());
        bundle.putString(CardData.RETAILER_NAME, cardData.getRetailerName());

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barcode = getArguments().getString(CardData.CARD_NUMBER);
        retailer = getArguments().getString(CardData.RETAILER_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.savings_fragment, container, false);
        return view;
    }
}
