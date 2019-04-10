package ir.ac.iust.appstore.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

public class ColorUtil
{
    public static Drawable colorizeDrawable(Context context, Drawable drawable, int color)
    {
        drawable.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(color), PorterDuff.Mode.SRC_ATOP));
        return drawable;
    }

    public static Drawable colorizeDrawable(Context context, Drawable drawable, int color,PorterDuff.Mode mode)
    {
        drawable.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(color), mode));
        return drawable;
    }
}
