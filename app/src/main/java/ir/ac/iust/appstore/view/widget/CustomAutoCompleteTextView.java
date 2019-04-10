package ir.ac.iust.appstore.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class CustomAutoCompleteTextView extends AppCompatAutoCompleteTextView
{
    public CustomAutoCompleteTextView(Context context)
    {
        super(context);
        if (!isInEditMode())
        {
            setTypeface(FontHelper.getInstance(context).getAppropriateFont());
        }
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        if (!isInEditMode())
        {
            setTypeface(FontHelper.getInstance(context).getAppropriateFont());
        }
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
        {
            setTypeface(FontHelper.getInstance(context).getAppropriateFont());
        }
    }
}
