package ir.ac.iust.appstore.model;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import ir.ac.iust.appstore.util.LocaleUtil;

public class AppContext
{
    //-----------------------------------------------Singleton part-------------------------------------------------------------

    private static AppContext appContext = new AppContext();

    private AppContext()
    {
    }

    public static AppContext getInstance()
    {
        return appContext;
    }

    //------------------------------------------------Variables------------------------------------------------------------------

    private boolean languageIsRTL;
    private boolean appContextInitialized;
    private ScreenSize screenSize;

    //this method initialize app context that contain application settings,screen size,imei,language direction ,...
    public void initAppContext(Context context)
    {
        if (!appContextInitialized)
        {
            //SCREEN SIZE
            screenSize = new ScreenSize();

            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            screenSize.setMetrics(metrics);

            //find screen size
            if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
                screenSize.setScreenSizeLevel(ScreenSize.ScreenSizeLevel.XLARGE);
            else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
                screenSize.setScreenSizeLevel(ScreenSize.ScreenSizeLevel.LARGE);
            else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
                screenSize.setScreenSizeLevel(ScreenSize.ScreenSizeLevel.MEDIUM);
            else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL)
                screenSize.setScreenSizeLevel(ScreenSize.ScreenSizeLevel.SMALL);

            // set locale according to current language
            try
            {
                context = LocaleUtil.setLocale(context, "fa");
                languageIsRTL = LocaleUtil.rtlLanguages.contains("fa");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            appContextInitialized = true;
        }
    }

    public ScreenSize getScreenSize()
    {
        return screenSize;
    }

    public void setScreenSize(ScreenSize screenSize)
    {
        this.screenSize = screenSize;
    }

    public boolean languageIsRTL()
    {
        return languageIsRTL;
    }

    public void setLanguageIsRTL(boolean languageIsRTL)
    {
        this.languageIsRTL = languageIsRTL;
    }

    // return 0.75 if it's LDPI
// return 1.0 if it's MDPI
// return 1.5 if it's HDPI
// return 2.0 if it's XHDPI
// return 3.0 if it's XXHDPI
// return 4.0 if it's XXXHDPI
}
