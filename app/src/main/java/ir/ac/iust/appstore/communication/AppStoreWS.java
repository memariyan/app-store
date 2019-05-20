package ir.ac.iust.appstore.communication;


import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ir.ac.iust.appstore.AppStoreApplication;
import ir.ac.iust.appstore.model.Application;
import ir.ac.iust.appstore.model.Comment;
import ir.ac.iust.appstore.model.api.API;
import ir.ac.iust.appstore.model.api.ApiCommunication;
import ir.ac.iust.appstore.model.api.ApiTaskHandler;
import ir.ac.iust.appstore.model.api.BaseTaskHandler;
import ir.ac.iust.appstore.model.api.JsonArrayWebServiceIO;
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
        getApplications(BASE_URL_ADDRESS+"/productData/",taskHandler);
    }

    public void getMostDownloadApplications(final BaseTaskHandler taskHandler)
    {
        getApplications(BASE_URL_ADDRESS+"/productData/MostDownload/",taskHandler);
    }

    public void getNewApplications(final BaseTaskHandler taskHandler)
    {
        getApplications(BASE_URL_ADDRESS+"/productData/NewApps/",taskHandler);
    }

    public void saveComment(Comment comment,final BaseTaskHandler taskHandler)
    {
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserRate",comment.getRate());
            jsonObject.put("User_Id",1);
            jsonObject.put("Product_Id",comment.getApplication().getId());
            jsonObject.put("Description",comment.getText());

            ApiCommunication.Builder apiCommunicationBuilder = new ApiCommunication.Builder(BASE_URL_ADDRESS+"/CommentData/Post", ApiCommunication.RequestMethod.GET);
            apiCommunicationBuilder.withInputReference(new JsonWebServiceIO(jsonObject));
            apiCommunicationBuilder.withOutputReference(new JsonWebServiceIO());
            apiCommunicationBuilder.withApiTaskHandler(new ApiTaskHandler()
            {
                @Override
                public void onSuccess(ApiCommunication apiCommunication)
                {
                    JSONObject result = (JSONObject) apiCommunication.getOutput().getData();
                    try
                    {
                        if(result.getBoolean("Succeed"))
                        {
                            taskHandler.onSuccess(result);
                        }
                        else
                        {
                            taskHandler.onFailure(result.getString("Exception"));
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(ApiCommunication apiCommunication,String reason)
                {
                    taskHandler.onFailure(reason);
                }
            });
            callWebService(apiCommunicationBuilder.build());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<Application> searchApplications(String text)
    {
        List<Application> result = new ArrayList<>();
        try
        {
            ApiCommunication.Builder apiCommunicationBuilder = new ApiCommunication.Builder(BASE_URL_ADDRESS+"/productData/Search?value="+text, ApiCommunication.RequestMethod.GET);
            apiCommunicationBuilder.withOutputReference(new JsonArrayWebServiceIO());

            ApiCommunication apiCommunication = callWebServiceAndGet(apiCommunicationBuilder.build());

            JSONArray jsonArray = (JSONArray) apiCommunication.getOutput().getData();
            for(int i=0;i<jsonArray.length();i++)
            {
                try
                {
                    JSONObject appJSON = jsonArray.getJSONObject(i);
                    result.add(getObjectResponse(appJSON, Application.class));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    private void getApplications(String url,final BaseTaskHandler taskHandler)
    {
        ApiCommunication.Builder apiCommunicationBuilder = new ApiCommunication.Builder(url, ApiCommunication.RequestMethod.GET);
        apiCommunicationBuilder.withOutputReference(new JsonArrayWebServiceIO());
        apiCommunicationBuilder.withApiTaskHandler(new ApiTaskHandler()
        {
            @Override
            public void onSuccess(ApiCommunication apiCommunication)
            {
                List<Application> result = new ArrayList<>();
                JSONArray jsonArray = (JSONArray) apiCommunication.getOutput().getData();
                for(int i=0;i<jsonArray.length();i++)
                {
                    try
                    {
                        JSONObject appJSON = jsonArray.getJSONObject(i);
                        result.add(getObjectResponse(appJSON, Application.class));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                taskHandler.onSuccess(result);
            }
            @Override
            public void onFailure(ApiCommunication apiCommunication,String reason)
            {
                taskHandler.onFailure(reason);
            }
        });

        callWebService(apiCommunicationBuilder.build());
    }

    public void getApplication(long applicationId , final BaseTaskHandler taskHandler)
    {
        ApiCommunication.Builder apiCommunicationBuilder = new ApiCommunication.Builder(BASE_URL_ADDRESS+"/productData/"+applicationId, ApiCommunication.RequestMethod.GET);
        apiCommunicationBuilder.withOutputReference(new JsonWebServiceIO());
        apiCommunicationBuilder.withApiTaskHandler(new ApiTaskHandler()
        {
            @Override
            public void onSuccess(ApiCommunication apiCommunication)
            {
                taskHandler.onSuccess(getObjectResponse((JSONObject) apiCommunication.getOutput().getData(),Application.class));
            }
            @Override
            public void onFailure(ApiCommunication apiCommunication,String reason)
            {
                taskHandler.onFailure(reason);
            }
        });
        callWebService(apiCommunicationBuilder.build());
    }

    private <T> T  getObjectResponse(JSONObject jsonObject,Class<T> reference)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            String json = jsonObject.toString();
            return mapper.readValue(json, reference);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
