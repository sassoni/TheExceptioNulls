package com.theexceptionulls.projectskullnbones.webservices;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.theexceptionulls.projectskullnbones.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rohithavatapally on 8/28/14.
 */
public class RegisterUser extends BaseWebService {

    private Context context;
    private String url;

    public RegisterUser(Context context, Handler callback, String credential, String opco) {
        this.context = context;

        //exceptionulls/register/customer/{cardId}/opco/{opco}/new

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.WEB_SERVICE_URL);
        stringBuilder.append("/exceptionulls/register/customer/");
        stringBuilder.append(credential);
        stringBuilder.append("/opco/");
        stringBuilder.append(opco);
        stringBuilder.append("/new");

        url = stringBuilder.toString();
        setCallback(callback);
        setUrl(url);
    }

    @Override
    public void execute() {
        super.execute();
    }

    @Override
    public boolean isPostRequest() {
        return true;
    }

    @Override
    public void parseJsonResponse(String jsonResponseString) {

        try {
            JSONObject jsonObject = new JSONObject(jsonResponseString);

            RegisterUserResponse registerUserResponse = new RegisterUserResponse();
            registerUserResponse.setSuccess(jsonObject.getBoolean("Success"));

            Message message = new Message();
            message.what = WEB_SERVICE_SUCCESS;
            message.obj = registerUserResponse;
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
