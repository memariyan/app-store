package ir.ac.iust.appstore.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.model.AppContext;
import ir.ac.iust.appstore.view.ViewTools;
import ir.ac.iust.appstore.view.adapter.SimpleFragmentPagerAdapter;
import ir.ac.iust.appstore.view.widget.CustomTextView;

public class CategoryFragment extends Fragment
{
    private TabLayout tabLayout;
    private ViewPager tabViewPager;

    public CategoryFragment()
    {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance()
    {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_category, container, false);

        tabViewPager = (ViewPager) rootView.findViewById(R.id.category_types_view_pager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.category_types_tabs);
        tabLayout.setupWithViewPager(tabViewPager);

        //setup fragments for tab
        SimpleFragmentPagerAdapter viewPagerAdapter = new SimpleFragmentPagerAdapter(getChildFragmentManager());
        boolean isRTL = AppContext.getInstance().languageIsRTL();
        if (AppContext.getInstance().languageIsRTL())
        {
            viewPagerAdapter.addFrag(CategoryItemsFragment.newInstance(),"Update apps");
            viewPagerAdapter.addFrag(CategoryItemsFragment.newInstance(),"All apps");
        }
        else
        {
            viewPagerAdapter.addFrag(CategoryItemsFragment.newInstance(),"All apps");
            viewPagerAdapter.addFrag(CategoryItemsFragment.newInstance(),"Update apps");
        }

        //update view pager
        tabViewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.notifyDataSetChanged();

        //setup tab icons
        if (isRTL)
        {
            configTab(1, "برنامه ها", R.drawable.icon_apps);
            configTab(0, "بازی ها", R.drawable.icon_games);

            tabViewPager.setCurrentItem(1);
        }
        else
        {
            configTab(0, "برنامه ها", R.drawable.icon_apps);
            configTab(1, "بازی ها", R.drawable.icon_games);

            tabViewPager.setCurrentItem(0);
        }
        ViewTools.setLTRSupportSettings(rootView);

        return rootView;
    }

    public void configTab(int index, String title, int iconRes)
    {
        //set tab text for current head category
        final CustomTextView headTab = (CustomTextView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_custom_tab, null);
        headTab.setText(title);

        //load icon on tab asynchronous
        final int iconWidthDp = (int) (1.7 * getResources().getDimension(R.dimen.activity_tab_height) / 9);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(((BitmapDrawable)getActivity().getResources().getDrawable(iconRes)).getBitmap(), iconWidthDp, iconWidthDp, false);
        headTab.setCompoundDrawablesWithIntrinsicBounds(null, new BitmapDrawable(getResources(), resizedBitmap), null, null);

        tabLayout.getTabAt(index).setCustomView(headTab);
    }
}
