package ir.ac.iust.appstore.fragment;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.model.Application;
import ir.ac.iust.appstore.view.ViewTools;
import ir.ac.iust.appstore.view.adapter.ApplicationGroupAdapter;
import ir.ac.iust.appstore.view.adapter.VerticalApplicationAdapter;

public class ApplicationCollectionFragment extends Fragment
{
    private RecyclerView appsRecyclerView;

    public ApplicationCollectionFragment()
    {
        // Required empty public constructor
    }


    public static ApplicationCollectionFragment newInstance()
    {
        ApplicationCollectionFragment fragment = new ApplicationCollectionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_application_list, container, false);

        List<Application> applications = new ArrayList<Application>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = getActivity().getPackageManager().queryIntentActivities( mainIntent, 0);
        for(ResolveInfo app : pkgAppsList)
        {
            applications.add(new Application(app.loadLabel(getActivity().getPackageManager()).toString(),app.loadIcon(getActivity().getPackageManager()),app.activityInfo.packageName));
        }

        VerticalApplicationAdapter appsAdapter = new VerticalApplicationAdapter(applications);
        appsRecyclerView = rootView.findViewById(R.id.apps_recycler_view);
        appsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false));
        appsRecyclerView.setAdapter(appsAdapter);

        ViewTools.setRTLSupportSettings(rootView);

        return rootView;
    }
}
