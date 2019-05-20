package ir.ac.iust.appstore.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.activity.AppInfoActivity;
import ir.ac.iust.appstore.model.AppContext;
import ir.ac.iust.appstore.model.Application;
import ir.ac.iust.appstore.view.widget.CustomButton;
import ir.ac.iust.appstore.view.widget.CustomTextView;

public class VerticalApplicationAdapter extends RecyclerView.Adapter<VerticalApplicationAdapter.AppGroupItemHolder>
{
    private List<Application> applications;

    public VerticalApplicationAdapter(List<Application> applications)
    {
        this.applications = applications;
    }

    @Override
    public AppGroupItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_vertical_app, parent, false);
        return new AppGroupItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppGroupItemHolder holder, final int position)
    {
        Context context = holder.itemView.getContext();
        Application application = applications.get(position);
        holder.title.setText(application.getName());
        holder.icon.setImageDrawable(application.getIcon());
    }

    @Override
    public int getItemCount()
    {
        return applications.size();
    }

    class AppGroupItemHolder extends RecyclerView.ViewHolder
    {
        CustomTextView title;
        ImageView icon;
        CustomButton actionBtn;

        AppGroupItemHolder(View view)
        {
            super(view);

            actionBtn = view.findViewById(R.id.action_btn);
            title = (CustomTextView) view.findViewById(R.id.app_name);
            icon = view.findViewById(R.id.app_icon);

            actionBtn.setOnClickListener(view1 ->
            {
                Context context = itemView.getContext();
                PackageManager pm =  itemView.getContext().getPackageManager();
                context.startActivity(pm.getLaunchIntentForPackage(applications.get(getAdapterPosition()).getPackageName()));
            });

            if (AppContext.getInstance().languageIsRTL())
            {
                icon.setImageResource(R.drawable.icon_chevron_left);
            }
        }
    }
}

