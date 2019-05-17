package ir.ac.iust.appstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import ir.ac.iust.appstore.AppStoreApplication;

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
}
