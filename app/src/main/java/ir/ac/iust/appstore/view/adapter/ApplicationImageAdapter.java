package ir.ac.iust.appstore.view.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.model.Comment;
import ir.ac.iust.appstore.view.ViewTools;
import ir.ac.iust.appstore.view.widget.CustomTextView;

public class ApplicationImageAdapter extends RecyclerView.Adapter<ApplicationImageAdapter.ImageItemHolder>
{
    private List<Drawable> images;

    public ApplicationImageAdapter(List<Drawable> images)
    {
        this.images = images;
    }

    @Override
    public ApplicationImageAdapter.ImageItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_app_image, parent, false);
        return new ApplicationImageAdapter.ImageItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ApplicationImageAdapter.ImageItemHolder holder, final int position)
    {
        Drawable image = images.get(position);
        holder.image.setImageDrawable(image);
    }

    @Override
    public int getItemCount()
    {
        return images.size();
    }

    class ImageItemHolder extends RecyclerView.ViewHolder
    {
        ImageView image;

        ImageItemHolder(View view)
        {
            super(view);
            image = (ImageView) view.findViewById(R.id.app_image);

        }
    }
}