package ir.ac.iust.appstore.activity;

import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import ir.ac.iust.appstore.util.LocaleUtil;

public class CustomAppCompatActivity extends AppCompatActivity
{
    @Override
    protected void attachBaseContext(Context context)
    {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N)
        {
            super.attachBaseContext(LocaleUtil.wrap(context, "fa"));
        }
        else
        {
            super.attachBaseContext(context);
        }
    }
}
