package ir.ac.iust.appstore.view.widget;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class CustomEditText extends AppCompatEditText
{
    public CustomEditText(Context context)
    {
        super(context);
        if (!isInEditMode()) {
            setTypeface(FontHelper.getInstance(context).getAppropriateFont());
        }
    }

    public CustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (!isInEditMode()) {
            setTypeface(FontHelper.getInstance(context).getAppropriateFont());
        }
    }

    public CustomEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (!isInEditMode()) {
            setTypeface(FontHelper.getInstance(context).getAppropriateFont());
        }
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        if (charSequence != null)
            charSequence = FormatHelper.mapNumberCharacters(charSequence.toString());

        super.setText(charSequence, bufferType);
    }

    public String getContentText()
    {
        return FormatHelper.convertPersianNumberToEnglish( super.getText().toString());
    }

    @Override
    public void setError(CharSequence error)
    {
        if(error!=null)
        {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(error);
            spannableStringBuilder.setSpan(new CustomTypefaceSpan("", FontHelper.getInstance(getContext()).getAppropriateFont()), 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            super.setError(spannableStringBuilder);
        }
    }
}
