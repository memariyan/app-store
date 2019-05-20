package ir.ac.iust.appstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import ir.ac.iust.appstore.AppStoreApplication;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Image
{
    public enum Type
    {
        LOGO, APP_IMAGE;

        @JsonValue
        public int value()
        {
            return ordinal();
        }
    }

    private static final String BASE_URL_ADDRESS= (AppStoreApplication.APP_HOST_ADDRESS+"/Content/images/iconProduct/");

    @JsonProperty("ImageName")
    private String name;

    @JsonProperty("Type")
    private Type type;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public String getUrl()
    {
        return BASE_URL_ADDRESS+name;
    }
}
