package com.theexceptionulls.projectskullnbones.Card;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.theexceptionulls.projectskullnbones.*;

import java.util.EnumMap;
import java.util.Map;

public class CardFragment extends Fragment {

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    private ImageView credentialBarcode;
    private TextView credentialNumber;
    private String barcode;
    private String retailer;
    private int gridPosition;
    private static boolean fromRegistration = false;

    int REQUEST = 11;

    public CardFragment() {
    }

    public static final CardFragment newInstance(Bundle bundle) {
        CardFragment fragment = new CardFragment();
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
            AppSettings.getInstance().addToCardDataList(new CardData(barcode, retailer));
        } else {
            fromRegistration = false;
            gridPosition = getArguments().getInt(Constants.LOYALTY_CARD_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.card_fragment, container, false);

        credentialBarcode = (ImageView) view.findViewById(R.id.card_fragment_barcode_image);
        credentialNumber = (TextView) view.findViewById(R.id.card_fragment_card_number);

        Button button = (Button) view.findViewById(R.id.card_fragment_card_image_edit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanActivity.class);
                startActivityForResult(intent, REQUEST);
            }
        });

        if (fromRegistration) {
            credentialNumber.setText(barcode);
            Bitmap bitmap = null;
            try {
                bitmap = encodeAsBitmap(barcode, com.google.zxing.BarcodeFormat.CODE_128, dpToPx(400), dpToPx(100));
                credentialBarcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else {
            CardData cardData = AppSettings.getInstance().getCardDataList().get(gridPosition);
            credentialNumber.setText(cardData.getCardNumber());
            Bitmap bitmap = null;
            try {
                bitmap = encodeAsBitmap(cardData.getCardNumber(), com.google.zxing.BarcodeFormat.CODE_128, dpToPx(400), dpToPx(100));
                credentialBarcode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST && resultCode == getActivity().RESULT_OK) {
            String barcode = data.getStringExtra(ScanActivity.BARCODE_VALUE_KEY);
            Toast.makeText(getActivity().getApplicationContext(), "Barcode " + barcode, Toast.LENGTH_LONG).show();

        }
    }

    Bitmap encodeAsBitmap(String contents, com.google.zxing.BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    private int dpToPx(float px) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.getDisplayMetrics());
    }

}
