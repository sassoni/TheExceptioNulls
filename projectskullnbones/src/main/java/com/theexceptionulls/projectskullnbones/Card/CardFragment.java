package com.theexceptionulls.projectskullnbones.Card;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.catalinamarketing.scanner.BarcodeScanner;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.theexceptionulls.projectskullnbones.*;
import com.theexceptionulls.projectskullnbones.webservices.BaseWebService;
import com.theexceptionulls.projectskullnbones.webservices.RegisterUser;
import com.theexceptionulls.projectskullnbones.webservices.RegisterUserResponse;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

public class CardFragment extends Fragment implements Handler.Callback {

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    private ImageView credentialBarcode;
    private TextView credentialNumber;
    private String barcode;
    private String retailer;
    private String retailerOpco;
    private int gridPosition;
    private static boolean fromRegistration = false;

    static final int REQUEST_BARCODE_CAPTURE = 11;
    static final int REQUEST_IMAGE_CAPTURE = 12;

    private LinearLayout logoLinearLayout;
    private ImageView logoImage;
    private ImageView cardPhoto;
    private Uri photoURI;

    private File photoFile = null;

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
        ActionBar actionBar = getActivity().getActionBar();

        if (intentFrom.equals(Constants.INTENT_FROM_REGISTRATION)) {
            fromRegistration = true;
            barcode = getArguments().getString(CardData.CARD_NUMBER);
            retailer = getArguments().getString(CardData.RETAILER_NAME);
            retailerOpco = AppSettings.getRetailerOpco(retailer);
            AppSettings.getInstance().addToCardDataList(new CardData(barcode, null, retailer, retailerOpco));
            actionBar.setTitle(retailer+" Card");
            //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(AppSettings.getColorRetailerColor(retailer))));
            actionBar.setDisplayHomeAsUpEnabled(true);

            RegisterUser registerUser = new RegisterUser(getActivity().getApplicationContext(), new Handler(this), barcode, retailerOpco);
            registerUser.execute();

        } else {
            fromRegistration = false;
            gridPosition = getArguments().getInt(Constants.LOYALTY_CARD_POSITION);
            barcode = AppSettings.getInstance().getCardDataList().get(gridPosition).getCardNumber();
            retailer = AppSettings.getInstance().getCardDataList().get(gridPosition).getRetailerName();
            photoURI = AppSettings.getInstance().getCardDataList().get(gridPosition).getPhotoUri();

            actionBar.setTitle(retailer + " Card");
            //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(AppSettings.getColorRetailerColor(retailer))));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.card_fragment, container, false);

        logoLinearLayout = (LinearLayout) view.findViewById(R.id.card_fragment_retailer_logo);
        logoImage = (ImageView) view.findViewById(R.id.card_fragment_retailer_logo_imageview);

        logoImage.setImageDrawable(AppSettings.getDrawable(getActivity().getApplicationContext(), retailer));
        logoLinearLayout.setBackgroundColor(Color.parseColor(AppSettings.getColorRetailerColor(retailer)));

        credentialBarcode = (ImageView) view.findViewById(R.id.card_fragment_barcode_image);
        credentialNumber = (TextView) view.findViewById(R.id.card_fragment_card_number);
        cardPhoto = (ImageView) view.findViewById(R.id.card_fragment_card_image);

        cardPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(getActivity());
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
            if (photoURI != null) {
                Bitmap scaledBitmap = scaledBitmapFromUri(photoURI);
                cardPhoto.setImageBitmap(scaledBitmap);
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        BarcodeScanner.getInstance().releaseScanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        AppController.loadBarcodeScanner();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {

            Uri uri = Uri.fromFile(photoFile);
            Bitmap scaledBitmap = scaledBitmapFromUri(uri);

            cardPhoto.setImageBitmap(scaledBitmap);

            // save it
            AppSettings.getInstance().setPhotoUriInCardDataWithName(retailer, uri);
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

    private void dispatchTakePictureIntent(Context context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else {
            }
        }
    }

    private File createImageFile() throws IOException {
        String mCurrentPhotoPath;
        // = getActivity().getExternalFilesDir(null).toString();

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private Bitmap scaledBitmapFromUri(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
            // We assume nothing wrong happens. Just because
        }
        int nh = (int) (bitmap.getHeight() * (640.0 / bitmap.getWidth()));
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 640, nh, true);
        return scaledBitmap;
    }

    @Override
    public boolean handleMessage(Message msg) {

        if (msg.what == BaseWebService.WEB_SERVICE_SUCCESS){

            if (msg.obj instanceof RegisterUserResponse){

                RegisterUserResponse registerUserResponse = (RegisterUserResponse) msg.obj;
                if (registerUserResponse.isSuccess()){
                    Intent intent = new Intent(Constants.REGISTRATION_BROADCAST_INTENT);
                    LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).sendBroadcast(intent);
                }
            }

        }

        return true;
    }
}
