package ir.ac.iust.appstore.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ir.ac.iust.appstore.view.widget.FontHelper;
import ir.ac.iust.appstore.view.widget.FormatHelper;

/**
 * Created by IT-GIS on 7/16/2016.
 */
public class LocaleUtil
{
    public static final List<String> rtlLanguages;

    static
    {
        rtlLanguages = new ArrayList<String>();
        rtlLanguages.add("fa");
    }

    public static Context setLocale(Context context, String langCode) throws Exception
    {
        context = wrap(context,langCode);

        //change font to appropriate instance
        FontHelper.setAppropriateFont(context);

        //change text format for selected language
        FormatHelper.setCurrentAppLanguage(langCode);

        return context;
    }


    public static ContextWrapper wrap(Context context,String language)
    {
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();
        Locale newLocale = new Locale(language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            configuration.setLocale(newLocale);
            LocaleList localeList = new LocaleList(newLocale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context = context.createConfigurationContext(configuration);
        }
        else
        {
            configuration.locale = newLocale;
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }
        return new ContextWrapper(context);
    }
}
