package ir.ac.iust.appstore.model;

import android.util.DisplayMetrics;

/**
 * Created by Mohammad on 5/25/2016.
 */
public class ScreenSize
{

    public enum ScreenSizeLevel
    {
        SMALL,
        MEDIUM,
        LARGE,
        XLARGE;
    }

    private static final float DEFAULT_ICON_SIZE = 0.2f;

    private ScreenSizeLevel screenSizeLevel;
    private DisplayMetrics metrics;

    public ScreenSizeLevel getScreenSizeLevel() {
        return screenSizeLevel;
    }

    public void setScreenSizeLevel(ScreenSizeLevel screenSizeLevel) {
        this.screenSizeLevel = screenSizeLevel;
    }

    public DisplayMetrics getMetrics() {
        return metrics;
    }

    public void setMetrics(DisplayMetrics metrics) {
        this.metrics = metrics;
    }

    public int getDefaultIconSize()
    {
        return (int) (metrics.density*DEFAULT_ICON_SIZE*60);
    }
}
