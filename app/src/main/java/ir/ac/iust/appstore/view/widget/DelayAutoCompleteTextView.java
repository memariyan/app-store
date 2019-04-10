package ir.ac.iust.appstore.view.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;

public class DelayAutoCompleteTextView extends CustomAutoCompleteTextView
{
    private static final int MESSAGE_TEXT_CHANGED = 100;
    private static final int DEFAULT_AUTOCOMPLETE_DELAY = 750;

    private int mAutoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY;
    private LoadingIndicator mLoadingIndicator;
    private String postfix = "";

    public interface LoadingIndicator
    {
        void onStartLoading();

        void onStopLoading();
    }

    private final Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            DelayAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
        }
    };

    public DelayAutoCompleteTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setLoadingIndicator(LoadingIndicator loadingIndicator)
    {
        mLoadingIndicator = loadingIndicator;
    }

    public void setAutoCompleteDelay(int autoCompleteDelay)
    {
        mAutoCompleteDelay = autoCompleteDelay;
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode)
    {
        if (mLoadingIndicator != null)
            mLoadingIndicator.onStartLoading();

        if (isPopupShowing())
            dismissDropDown();

        mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT_CHANGED, text + postfix), mAutoCompleteDelay);
    }

    @Override
    public void onFilterComplete(int count)
    {
        if (mLoadingIndicator != null)
            mLoadingIndicator.onStopLoading();

        if (count > 0)
            showDropDown();

        super.onFilterComplete(count);
    }

    @Override
    public void setError(CharSequence error)
    {
        if (error != null)
        {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(error);
            spannableStringBuilder.setSpan(new CustomTypefaceSpan("", FontHelper.getInstance(getContext()).getAppropriateFont()), 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            super.setError(spannableStringBuilder);
        }
    }

    public void setPostfix(String postfix)
    {
        this.postfix = postfix;
    }
}
