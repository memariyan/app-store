package ir.ac.iust.appstore.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ir.ac.iust.appstore.R;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Mohammad on 5/21/2016.
 */
public class ViewTools
{
    public enum ViewDirection
    {
        RTL(1), LTR(2);
        public int value;

        ViewDirection(int value)
        {
            this.value = value;
        }
    }

    public static void setViewMargins(View v, int l, int t, int r, int b)
    {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)
        {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static Drawable getDrawableIcon(int color, IIcon icon, int size, Context context)
    {
        return new IconicsDrawable(context).color(context.getResources().getColor(color)).icon(icon).sizeDp(size);
    }

    /**
     * In this method, the toolbar icons are arranged according to the language state of the user
     * @param activity
     * @param toolbar
     * @return
     */
    public static Toolbar setToolbarBackButton(AppCompatActivity activity, Toolbar toolbar)
    {
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(new IconicsDrawable(activity).icon(MaterialDesignIconic.Icon.gmi_long_arrow_right).sizeDp(20).color(activity.getResources().getColor(R.color.white)));

        return toolbar;
    }

    /**
     * set full screen view from android 5
     * @param activity
     */
    public static void enableFullScreenMode(Activity activity)
    {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 21)
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * set status color from android 5
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, int color)
    {
        if (Build.VERSION.SDK_INT >= 21)
            activity.getWindow().setStatusBarColor(color);
    }

    /**
     * set design right to left for view
     * @param view
     */
    public static void setRTLSupportSettings(View view)
    {
        ViewCompat.setLayoutDirection(view, ViewCompat.LAYOUT_DIRECTION_RTL);
    }

    /**
     * set design left to right for view
     * @param view
     */
    public static void setLTRSupportSettings(View view)
    {
        ViewCompat.setLayoutDirection(view, ViewCompat.LAYOUT_DIRECTION_LTR);
    }

    public static void injectCustomFont(Context context, String customFontName, String fontAssetName)
    {
        final Typeface fontFamily = Typeface.createFromAsset(context.getAssets(), fontAssetName);
        injectTypeface(customFontName, fontFamily);
    }

    public static void setDefaultFont(Context context, String systemFont, String fontAssetName)
    {
        final Typeface fontFamily = Typeface.createFromAsset(context.getAssets(), fontAssetName);
        replaceFont(systemFont, fontFamily);
    }

    private static void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface)
    {
        try
        {
            final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    private static boolean injectTypeface(String fontFamily, Typeface typeface)
    {
        try
        {
            Field field = Typeface.class.getDeclaredField("sSystemFontMap");
            field.setAccessible(true);
            Object fieldValue = field.get(null);
            Map<String, Typeface> map = (Map<String, Typeface>) fieldValue;
            map.put(fontFamily, typeface);
            return true;
        }
        catch (Exception e)
        {
            Log.e("Font-Injection", "Failed to inject typeface.", e);
        }
        return false;
    }

    /**
     * util for convert dp to pixels
     * @param dp
     * @param context
     * @return
     */
    public static float convertDpToPixel(float dp, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }
}
