package ir.ac.iust.appstore.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.communication.AppStoreWS;
import ir.ac.iust.appstore.model.Application;
import ir.ac.iust.appstore.model.Group;
import ir.ac.iust.appstore.model.api.BaseTaskHandler;
import ir.ac.iust.appstore.view.ViewTools;
import ir.ac.iust.appstore.view.adapter.ApplicationGroupAdapter;
import ir.ac.iust.appstore.view.adapter.SimpleFragmentPagerAdapter;
import ir.ac.iust.appstore.view.widget.CustomViewPager;

public class HomeFragment extends Fragment
{
    private SimpleFragmentPagerAdapter imageViewPagerAdapter;
    private CustomViewPager imageViewPager;
    private RecyclerView appsGroupRecyclerView;
    private List<Group> groups;

    public HomeFragment()
    {
    }

    public static HomeFragment newInstance()
    {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView= inflater.inflate(R.layout.fragment_home, container, false);

        imageViewPagerAdapter = new SimpleFragmentPagerAdapter(getChildFragmentManager());
        imageViewPager = new CustomViewPager(imageViewPagerAdapter, CustomViewPager.ImageAspectRatio.RATIO_16_9, getActivity()).withDots().withAutoSlideShow(5000).withNavigateBtn(true);
        ViewGroup topViewPager = (ViewGroup) rootView.findViewById(R.id.home_view_pager_layout);
        topViewPager.addView(imageViewPager.getView());
        showSlides();

        groups = new ArrayList<Group>();
        groups.add(new Group("جدیدترین ها",new ArrayList<Application>()));
        groups.add(new Group("محبوب ترین ها",new ArrayList<Application>()));

        ApplicationGroupAdapter appsGroupAdapter = new ApplicationGroupAdapter(groups);
        appsGroupRecyclerView = (RecyclerView) rootView.findViewById(R.id.apps_group_recycler_view);
        appsGroupRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false));
        appsGroupRecyclerView.setAdapter(appsGroupAdapter);

        AppStoreWS.getInstance().getNewApplications(new BaseTaskHandler()
        {
            @Override
            public void onSuccess(Object... results)
            {
                groups.get(0).setApplications((List<Application>) results[0]);
                appsGroupAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(String reason)
            { }
        });

        AppStoreWS.getInstance().getMostDownloadApplications(new BaseTaskHandler()
        {
            @Override
            public void onSuccess(Object... results)
            {
                groups.get(1).setApplications((List<Application>) results[0]);
                appsGroupAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(String reason)
            { }
        });
        ViewTools.setRTLSupportSettings(rootView);

        return rootView;
    }

    private void showSlides()
    {
        SliderImageFragment sliderImageFragment1 = new SliderImageFragment();
        sliderImageFragment1.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.home_slider_img_1));
        imageViewPagerAdapter.addFrag(sliderImageFragment1, "1");

        SliderImageFragment sliderImageFragment2 = new SliderImageFragment();
        sliderImageFragment2.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.home_slider_img_2));
        imageViewPagerAdapter.addFrag(sliderImageFragment2, "2");

        /*SliderImageFragment sliderImageFragment3 = new SliderImageFragment();
        sliderImageFragment3.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.home_slider_img_3));
        imageViewPagerAdapter.addFrag(sliderImageFragment3, "3");*/

        imageViewPagerAdapter.notifyDataSetChanged();
    }
}
