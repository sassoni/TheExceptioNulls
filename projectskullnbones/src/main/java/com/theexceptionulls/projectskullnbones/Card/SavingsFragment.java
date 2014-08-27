package com.theexceptionulls.projectskullnbones.Card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.theexceptionulls.projectskullnbones.R;

/**
 * Created by rohithavatapally on 8/27/14.
 */
public class SavingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.savings_fragment, container, false);
        return view;
    }
}
