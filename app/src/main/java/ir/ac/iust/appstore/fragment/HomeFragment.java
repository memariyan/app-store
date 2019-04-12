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
import ir.ac.iust.appstore.model.Application;
import ir.ac.iust.appstore.model.ApplicationGroup;
import ir.ac.iust.appstore.view.ViewTools;
import ir.ac.iust.appstore.view.adapter.AppsGroupAdapter;
import ir.ac.iust.appstore.view.adapter.SimpleFragmentPagerAdapter;
import ir.ac.iust.appstore.view.widget.CustomViewPager;

public class HomeFragment extends Fragment
{
    private SimpleFragmentPagerAdapter imageViewPagerAdapter;
    private CustomViewPager imageViewPager;
    private RecyclerView appsGroupRecyclerView;

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

        List<Application> applicationsGroup1 = new ArrayList<Application>();
        applicationsGroup1.add(new Application("Snapp",R.drawable.icon_app_snapp));
        applicationsGroup1.add(new Application("Torob",R.drawable.icon_app_torob));
        applicationsGroup1.add(new Application("Taghche",R.drawable.icon_app_taghche));
        applicationsGroup1.add(new Application("Komod",R.drawable.icon_app_komod));


        List<ApplicationGroup> applicationGroups = new ArrayList<ApplicationGroup>();
        applicationGroups.add(new ApplicationGroup("برنامه های برگزیده",applicationsGroup1));
        applicationGroups.add(new ApplicationGroup("برنامه های محبوب",new ArrayList<>()));
        applicationGroups.add(new ApplicationGroup("برنامه های پر دانلود",new ArrayList<>()));

        AppsGroupAdapter appsGroupAdapter = new AppsGroupAdapter(applicationGroups);
        appsGroupRecyclerView = (RecyclerView) rootView.findViewById(R.id.apps_group_recycler_view);
        appsGroupRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false));
        appsGroupRecyclerView.setAdapter(appsGroupAdapter);

        ViewTools.setRTLSupportSettings(rootView);

        return rootView;
    }

    private void showSlides()
    {
        SliderImageFragment sliderImageFragment1 = new SliderImageFragment();
        sliderImageFragment1.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.home_slider_img_1));
        imageViewPagerAdapter.addFrag(sliderImageFragment1, "1");

   /*     SliderImageFragment sliderImageFragment2 = new SliderImageFragment();
        sliderImageFragment2.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.home_slider_img_2));
        imageViewPagerAdapter.addFrag(sliderImageFragment2, "2");

        SliderImageFragment sliderImageFragment3 = new SliderImageFragment();
        sliderImageFragment3.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.home_slider_img_3));
        imageViewPagerAdapter.addFrag(sliderImageFragment3, "3");*/

        imageViewPagerAdapter.notifyDataSetChanged();
    }
}
