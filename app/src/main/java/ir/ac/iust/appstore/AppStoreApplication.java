package ir.ac.iust.appstore;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import ir.ac.iust.appstore.model.AppContext;
import ir.ac.iust.appstore.util.LocaleUtil;

public class AppStoreApplication extends Application
{
    public static final String APP_HOST_ADDRESS="http://192.168.1.2:16913";

    @Override
    public void onCreate()
    {
        super.onCreate();

        Context context=getApplicationContext();

        //initialize appContext
        AppContext.getInstance().initAppContext(context);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        //set language again when config was changed
        try
        {
            LocaleUtil.setLocale(getApplicationContext(), "fa");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
