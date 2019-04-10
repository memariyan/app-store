package ir.ac.iust.appstore.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class CustomTextView extends AppCompatTextView
{
    public CustomTextView(Context context)
    {
        super(context);
        if (!isInEditMode())
            setFont(context);
    }

    public CustomTextView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        if (!isInEditMode())
            setFont(context);
    }

    public CustomTextView(Context context, AttributeSet attributeSet, int i)
    {
        super(context, attributeSet, i);
        if (!isInEditMode())
            setFont(context);
    }

    private void setFont(Context context)
    {
        setTypeface(FontHelper.getInstance(context).getAppropriateFont());
    }

    public void setText(CharSequence charSequence, BufferType bufferType)
    {
        if (charSequence != null)
            charSequence = FormatHelper.mapNumberCharacters(charSequence.toString());

        super.setText(charSequence, bufferType);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }
}
