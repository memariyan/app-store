package ir.ac.iust.appstore.model.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohammad on 2016-10-10.
 */
public class ApiCommunication
{
    public enum RequestMethod
    {
        GET,
        POST,
        UPDATE,
        DELETE;
    }

    private RequestMethod requestMethodKind;
    private String requestBaseUrl;
    private WebServiceIO input;
    private WebServiceIO output;
    private List<Pair<String,String>> requestParameters;
    private List<Pair<String,String>> requestProperties;
    private Map<String,List<String>> headerFields;
    private ApiTaskHandler apiTaskHandler;
    private String responseStatus;
    private int responseCode;
    private int triedConnectCount;

    private ApiCommunication(Builder builder)
    {
        this.requestMethodKind=builder.requestMethodKind;
        this.requestBaseUrl=builder.requestBaseUrl;
        this.input=builder.input;
        this.output=builder.output;
        this.apiTaskHandler =builder.apiTaskHandler;
        this.requestParameters=builder.requestParameters;
        this.requestProperties=builder.requestProperties;
        this.headerFields=builder.headerFields;
        this.triedConnectCount=1;
    }

    public boolean isRequestPropertySet(String key)
    {
        for(Pair pair : requestProperties)
            if(pair.getLeft().toString().toLowerCase().equals(key.toLowerCase()))
                return true;

        return false;
    }

    public RequestMethod getRequestMethodKind()
    {
        return requestMethodKind;
    }

    public WebServiceIO getInput()
    {
        return input;
    }

    public WebServiceIO getOutput()
    {
        return output;
    }

    public String getBaseUrl()
    {
        return requestBaseUrl;
    }

    public List<Pair<String, String>> getRequestParameters()
    {
        return requestParameters;
    }

    public List<Pair<String, String>> getRequestProperties()
    {
        return requestProperties;
    }

    public void addProperty(String name, String value)
    {
        this.requestProperties.add(new Pair<String, String>(name,value));
    }

    public void addParameter(String name, String value)
    {
        this.requestParameters.add(new Pair<String, String>(name,value));
    }

    public ApiTaskHandler getApiTaskHandler()
    {
        return apiTaskHandler;
    }

    public int getTriedConnectCount()
    {
        return triedConnectCount;
    }

    public void addTriedConnectCount()
    {
        triedConnectCount++;
    }

    public Map<String, List<String>> getHeaderFields()
    {
        return headerFields;
    }

    public String getResponseStatus()
    {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus)
    {
        this.responseStatus = responseStatus;
    }

    public int getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(int responseCode)
    {
        this.responseCode = responseCode;
    }

    public void setHeaderFields(Map<String, List<String>> headerFields)
    {
        this.headerFields = headerFields;
    }

    public void setApiTaskHandler(ApiTaskHandler apiTaskHandler)
    {
        this.apiTaskHandler = apiTaskHandler;
    }

    public static class Builder
    {
        private RequestMethod requestMethodKind;
        private String requestBaseUrl;
        private WebServiceIO input;
        private WebServiceIO output;
        private List<Pair<String,String>> requestParameters;
        private List<Pair<String,String>> requestProperties;
        private ApiTaskHandler apiTaskHandler;
        private Map<String,List<String>> headerFields;

        public Builder(String requestBaseUrl, RequestMethod requestMethodKind, ApiTaskHandler apiTaskHandler)
        {
            this(requestBaseUrl,requestMethodKind);
            this.apiTaskHandler=apiTaskHandler;
        }

        public Builder(String requestBaseUrl, RequestMethod requestMethodKind)
        {
            this.requestBaseUrl=requestBaseUrl;
            this.requestMethodKind=requestMethodKind;
            this.requestParameters=new ArrayList<>();
            this.requestProperties=new ArrayList<>();
        }

        public Builder withInputReference(WebServiceIO input)
        {
            this.input=input;
            return this;
        }

        public Builder withOutputReference(WebServiceIO output)
        {
            this.output=output;
            return this;
        }

        public Builder withApiTaskHandler(ApiTaskHandler apiTaskHandler)
        {
            this.apiTaskHandler=apiTaskHandler;
            return this;
        }

        public Builder addParameter(String name, String value)
        {
            this.requestParameters.add(new Pair<String, String>(name,value));
            return this;
        }

        public Builder addProperty(String name, String value)
        {
            this.requestProperties.add(new Pair<String, String>(name,value));
            return this;
        }

        public Builder withParameters(List<Pair<String,String>> parameters)
        {
            this.requestParameters=parameters;
            return this;
        }

        public Builder withProperties(List<Pair<String,String>> parameters)
        {
            this.requestParameters=parameters;
            return this;
        }

        public Builder withHeaderFields(Map<String,List<String>> headerFields)
        {
            this.headerFields =headerFields;
            return this;
        }

        public ApiCommunication build()
        {
            return new ApiCommunication(this);
        }
    }
}
