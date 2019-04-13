package ir.ac.iust.appstore.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class CustomButton extends AppCompatButton
{
    public CustomButton(Context context)
    {
        super(context);
        if (!isInEditMode())
        {
            setTypeface(FontHelper.getInstance(context).getAppropriateFont());
        }
    }

    public CustomButton(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        if (!isInEditMode())
        {
            setTypeface(FontHelper.getInstance(context).getAppropriateFont());
        }
    }

    public CustomButton(Context context, AttributeSet attributeSet, int i)
    {
        super(context, attributeSet, i);
        if (!isInEditMode())
        {
            setTypeface(FontHelper.getInstance(context).getAppropriateFont());
        }
    }

    public void setText(CharSequence charSequence, BufferType bufferType)
    {
        super.setText(FormatHelper.convertEnglishNumberToPersian(charSequence.toString()), bufferType);
    }
}
