package ir.ac.iust.appstore.view.widget;

import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by IT-GIS on 8/17/2016.
 */
public class ProgressBarLoadingIndicator implements DelayAutoCompleteTextView.LoadingIndicator
{
    ProgressBar progressBar;

    public ProgressBarLoadingIndicator(ProgressBar progressBar)
    {
        this.progressBar=progressBar;
    }

    @Override
    public void onStartLoading()
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopLoading()
    {
        progressBar.setVisibility(View.GONE);
    }
}
