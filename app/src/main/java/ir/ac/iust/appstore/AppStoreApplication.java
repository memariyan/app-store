package ir.ac.iust.appstore;

import android.app.Application;
import android.content.res.Configuration;

import ir.ac.iust.appstore.util.LocaleUtil;

public class AppStoreApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        try
        {
            LocaleUtil.setLocale(getApplicationContext(), "fa");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
