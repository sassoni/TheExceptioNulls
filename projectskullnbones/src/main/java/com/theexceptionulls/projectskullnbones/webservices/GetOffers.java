package com.theexceptionulls.projectskullnbones.webservices;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.theexceptionulls.projectskullnbones.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohithavatapally on 8/28/14.
 */
public class GetOffers extends BaseWebService {

    private static String url = null;
    private Context context;

    public GetOffers(Context context, Handler handler, String customerCredential, String opco) {
        this.context = context;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.WEB_SERVICE_URL);
        stringBuilder.append("/exceptionulls/offers/customer/");
        stringBuilder.append(customerCredential);
        stringBuilder.append("/opco/");
        stringBuilder.append(opco);
        stringBuilder.append("/get");

        url = stringBuilder.toString();
        setCallback(handler);
        setUrl(url);
    }

    @Override
    public void execute() {
        super.execute();
    }

    @Override
    public boolean isPostRequest() {
        return false;
    }

    @Override
    public void parseJsonResponse(String jsonResponseString) {

        try {

            JSONArray jsonArray = new JSONArray(jsonResponseString);

            List<Offers> offersList = new ArrayList<Offers>();

            for (int i = 0; i< jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Offers offers = new Offers();
                offers.setDescription(jsonObject.getString("description"));
                offers.setExpiration(jsonObject.getString("expiration"));
                offers.setHeading(jsonObject.getString("heading"));
                offers.setId(Integer.valueOf(jsonObject.getString("id")));
                offers.setImageUrl(jsonObject.getString("url"));
                offersList.add(offers);
            }

            GetCouponOffersResponse getCouponOffersResponse = new GetCouponOffersResponse();
            getCouponOffersResponse.setOffersList(offersList);

            Message message = new Message();
            message.what = WEB_SERVICE_SUCCESS;
            message.obj = getCouponOffersResponse;
            getCallback().sendMessage(message);

        } catch (JSONException e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = WEB_SERVICE_FAILURE;
            getCallback().sendMessage(message);
        }

    }

    @Override
    public Context getApplicationContext() {
        return context;
    }
}
