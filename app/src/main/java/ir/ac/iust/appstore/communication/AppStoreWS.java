package ir.ac.iust.appstore.communication;


import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ir.ac.iust.appstore.AppStoreApplication;
import ir.ac.iust.appstore.model.Application;
import ir.ac.iust.appstore.model.api.API;
import ir.ac.iust.appstore.model.api.ApiCommunication;
import ir.ac.iust.appstore.model.api.ApiTaskHandler;
import ir.ac.iust.appstore.model.api.BaseTaskHandler;
import ir.ac.iust.appstore.model.api.JsonWebServiceIO;
import ir.ac.iust.appstore.model.api.TextPlainWebServiceIO;
import ir.ac.iust.appstore.model.api.WebService;

public class AppStoreWS extends WebService implements API
{
    private static final String BASE_URL_ADDRESS= AppStoreApplication.APP_HOST_ADDRESS+"/api";

    private static AppStoreWS instance = new AppStoreWS();

    private AppStoreWS()
    {
    }

    public static AppStoreWS getInstance()
    {
        return instance;
    }

    public void getAllApplications(final BaseTaskHandler taskHandler)
    {
        ApiCommunication.Builder apiCommunicationBuilder = new ApiCommunication.Builder(BASE_URL_ADDRESS+"/productData/", ApiCommunication.RequestMethod.GET);
        apiCommunicationBuilder.withOutputReference(new JsonWebServiceIO());
        apiCommunicationBuilder.withApiTaskHandler(new ApiTaskHandler()
        {
            @Override
            public void onSuccess(ApiCommunication apiCommunication)
            {
                taskHandler.onSuccess(getObjectResponse(apiCommunication, Application.class));
            }
            @Override
            public void onFailure(ApiCommunication apiCommunication,String reason)
            {
                taskHandler.onFailure(reason);
            }
        });

        callWebService(apiCommunicationBuilder.build());
    }

    private <T> T  getObjectResponse(ApiCommunication apiCommunication,Class<T> reference)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            String json = apiCommunication.getOutput().getData().toString();
            return mapper.readValue(json, reference);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
