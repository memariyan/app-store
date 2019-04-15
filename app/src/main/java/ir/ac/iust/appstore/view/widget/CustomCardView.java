package ir.ac.iust.appstore.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mikepenz.materialize.util.UIUtils;

import androidx.cardview.widget.CardView;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.view.ViewTools;

/**
 * Created by ho on 4/12/2018.
 */

public class CustomCardView extends CardView
{
    public CustomCardView(Context context)
    {
        super(context);
        init(context);
    }

    public CustomCardView(final Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public void init(Context context)
    {
        try
        {
            setCardElevation(UIUtils.convertDpToPixel(2,context));

            ImageView imageView = new ImageView(getContext());
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, (int) ViewTools.convertDpToPixel(3f, getContext()));

            imageView.setLayoutParams(params);
            int color = getResources().getColor(R.color.colorPrimary);
            imageView.setBackgroundColor(color);

            addView(imageView);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
