package com.theexceptionulls.projectskullnbones.Card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.theexceptionulls.projectskullnbones.R;
import com.theexceptionulls.projectskullnbones.ScanActivity;

/**
 * Created by rohithavatapally on 8/27/14.
 */
public class CardFragment extends Fragment {

    int REQUEST = 11;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.card_fragment, container, false);

        Button button = (Button) view.findViewById(R.id.card_fragment_card_image_edit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanActivity.class);
                startActivityForResult(intent, REQUEST);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST && resultCode == getActivity().RESULT_OK){

            String barcode = data.getStringExtra(ScanActivity.BARCODE_VALUE_KEY);
            Toast.makeText(getActivity().getApplicationContext(), "Barcode "+barcode, Toast.LENGTH_LONG).show();

        }
    }
}
