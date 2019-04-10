package ir.ac.iust.appstore.view.widget;

import android.content.Context;
import android.graphics.Typeface;

public class FontHelper
{
    private static FontHelper instance;
    private static Typeface appropriateFont;

    private FontHelper()
    {
    }

    public static synchronized FontHelper getInstance(Context context)
    {
        FontHelper fontHelper;
        synchronized (FontHelper.class)
        {
            if (instance == null)
            {
                instance = new FontHelper();
                setAppropriateFont(context);
            }
            fontHelper = instance;
        }
        return fontHelper;
    }

    public Typeface getAppropriateFont()
    {
        return appropriateFont;
    }

    public static void setAppropriateFont(Context context)
    {
/*
        AppSettings appSettings=AppContext.getInstance().getAppSettings();
        String language=appSettings.getLanguage();
*/

        String language ="fa";
        if(language.equals("fa"))
            appropriateFont = Typeface.createFromAsset(context.getAssets(), "fonts/Yekan.ttf");
        else
            appropriateFont = Typeface.DEFAULT;
    }
}
