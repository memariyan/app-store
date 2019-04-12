package ir.ac.iust.appstore.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.model.AppContext;
import ir.ac.iust.appstore.model.ApplicationGroup;
import ir.ac.iust.appstore.view.widget.CustomTextView;

public class AppsGroupAdapter extends RecyclerView.Adapter<AppsGroupAdapter.AppGroupItemHolder>
{
    private List<ApplicationGroup> applicationGroups;

    public AppsGroupAdapter(List<ApplicationGroup> applicationGroups)
    {
        this.applicationGroups = applicationGroups;
    }

    @Override
    public AppGroupItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_apps_group, parent, false);
        return new AppGroupItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppGroupItemHolder holder, final int position)
    {
        Context context = holder.itemView.getContext();
        ApplicationGroup applicationGroup = applicationGroups.get(position);
        holder.title.setText(applicationGroup.getTitle());

        ApplicationAdapter applicationAdapter = new ApplicationAdapter(applicationGroup.getApplications());
        holder.appsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.appsRecyclerView.setHasFixedSize(true);
        holder.appsRecyclerView.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
        holder.appsRecyclerView.setAdapter(applicationAdapter);
    }

    @Override
    public int getItemCount()
    {
        return applicationGroups.size();
    }

    class AppGroupItemHolder extends RecyclerView.ViewHolder
    {
        CustomTextView title;
        ImageView allBtnChevron;
        RecyclerView appsRecyclerView;

        AppGroupItemHolder(View view)
        {
            super(view);

            title = (CustomTextView) view.findViewById(R.id.app_group_name);
            allBtnChevron = view.findViewById(R.id.all_apps_btn_chevron_icon);
            appsRecyclerView = view.findViewById(R.id.apps_recycler_view);

            if(AppContext.getInstance().languageIsRTL())
            {
                allBtnChevron.setImageResource(R.drawable.icon_chevron_left);
            }
        }
    }
}
