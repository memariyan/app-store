package ir.ac.iust.appstore.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.model.ExecutableTaskHandler;

public class SliderImageFragment extends Fragment
{
    private ImageView imageView;
    private Bitmap image;
    private ExecutableTaskHandler onCreateViewListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_slider_image, container, false);
        imageView = view.findViewById(R.id.imageView);

        if(image!=null)
        {
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), image);
            dr.setCornerRadius(30);
            imageView.setImageDrawable(dr);
            //imageView.setImageBitmap(image);
        }

        if(onCreateViewListener!=null)
            onCreateViewListener.execute(imageView);

        return view;
    }

    public void setOnCreateViewListener(ExecutableTaskHandler onCreateViewListener)
    {
        this.onCreateViewListener = onCreateViewListener;
    }

    public void setImage(Bitmap image)
    {
        this.image = image;
    }
}
