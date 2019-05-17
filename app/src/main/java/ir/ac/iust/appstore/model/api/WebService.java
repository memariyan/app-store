package ir.ac.iust.appstore.model.api;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*this class is a base class for other web service class such as SavisWS and contain basic methods to call a method from web service*/
public class WebService
{
    protected static final String SUCCESS_RESPONSE="SUCCESS_RESPONSE";
    protected static final String ERROR_RESPONSE="ERROR_RESPONSE";
    protected static final String UNAUTHORIZED_ERROR_RESPONSE="UNAUTHORIZED_ERROR_RESPONSE";
    public static final String DATA_NOT_CHANGED="DATA_NOT_CHANGED";

    //call web service with raw url and parameter and handler
    public void callWebService(ApiCommunication apiCommunication)
    {
        apiCommunication.addTriedConnectCount();
        new WSRequestAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,apiCommunication);
    }

    public ApiCommunication callWebServiceAndGet(ApiCommunication apiCommunication) throws Exception
    {
        apiCommunication.addTriedConnectCount();
        return new WSRequestAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,apiCommunication).get();
    }

    //async task class for call web service
    private class WSRequestAsyncTask extends AsyncTask<ApiCommunication, Void , ApiCommunication>
    {
        @Override
        protected ApiCommunication doInBackground(ApiCommunication... params)
        {
            ApiCommunication apiCommunication=params[0];
            try
            {
                //this parameter save connection status response such as success or some error kind
                String connectionStatus="";

                //------------------------------------------build url---------------------------------------------------------

                Uri.Builder uriBuilder= Uri.parse(apiCommunication.getBaseUrl()).buildUpon();
                if(apiCommunication.getRequestMethodKind().equals(ApiCommunication.RequestMethod.GET))
                {
                    for(Pair<String,String> parameter : apiCommunication.getRequestParameters())
                        uriBuilder.appendQueryParameter(parameter.getLeft(), parameter.getRight());
                }
                URL url=new URL(uriBuilder.build().toString());

                //-----------------------------------Create Http Connection------------------------------------------------
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestProperty("User-Agent", "Android HTTP Client 1.0");
                urlConnection.setConnectTimeout(1000);

                if(apiCommunication.getInput()!=null && !apiCommunication.isRequestPropertySet("content-type"))
                    urlConnection.setRequestProperty("content-type", apiCommunication.getInput().getMimeType().value);

                urlConnection.setRequestMethod(apiCommunication.getRequestMethodKind().toString());

                //Set request properties
                for(Pair<String,String> parameter : apiCommunication.getRequestProperties())
                    urlConnection.setRequestProperty(parameter.getLeft(), parameter.getRight());

                //put input data to output stream
                if(apiCommunication.getInput()!=null)
                {
                    urlConnection.setDoOutput(true);
                    apiCommunication.getInput().writeTo(urlConnection.getOutputStream());
                }

                apiCommunication.setHeaderFields(urlConnection.getHeaderFields());

                if(urlConnection.getResponseCode()== HttpURLConnection.HTTP_OK)
                {
                    //fetch result data
                    if(apiCommunication.getOutput()!=null)
                        apiCommunication.getOutput().readFrom(new BufferedInputStream(urlConnection.getInputStream()));

                    connectionStatus=SUCCESS_RESPONSE;
                }

                else if(urlConnection.getResponseCode()== HttpURLConnection.HTTP_UNAUTHORIZED)
                    connectionStatus=UNAUTHORIZED_ERROR_RESPONSE;

                else if(urlConnection.getResponseCode()== HttpURLConnection.HTTP_NOT_MODIFIED)
                    connectionStatus=DATA_NOT_CHANGED;

                else
                    connectionStatus=ERROR_RESPONSE+"_"+urlConnection.getResponseCode()+"_"+urlConnection.getResponseMessage();

                //disconnect
                urlConnection.disconnect();

                apiCommunication.setResponseStatus(connectionStatus);
            }
            catch (Exception e)
            {
                apiCommunication.setResponseStatus("error : " + e.getMessage());
            }

            return apiCommunication;
        }

        @Override
        protected void onPostExecute(ApiCommunication apiCommunication)
        {
            System.out.println(apiCommunication.getResponseStatus()+"------responseStatus---------"+apiCommunication.getBaseUrl());
            if(apiCommunication.getApiTaskHandler()!=null)
            {
                if(apiCommunication.getResponseStatus().equals(SUCCESS_RESPONSE))

                    apiCommunication.getApiTaskHandler().onSuccess(apiCommunication);

                else
                    apiCommunication.getApiTaskHandler().onFailure(apiCommunication,apiCommunication.getResponseStatus());
            }
        }
    }
}
