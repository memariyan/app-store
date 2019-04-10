package ir.ac.iust.appstore.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.fragment.HomeFragment;
import ir.ac.iust.appstore.model.OnTabClickListener;
import ir.ac.iust.appstore.model.Tab;
import ir.ac.iust.appstore.view.ViewTools;
import ir.ac.iust.appstore.view.adapter.SimpleFragmentPagerAdapter;
import ir.ac.iust.appstore.view.widget.CustomTextView;
import ir.ac.iust.appstore.view.widget.FontHelper;

public class MainActivity extends AppCompatActivity implements OnTabClickListener
{
    private Drawer drawer;
    private AccountHeader headerDrawer;
    private Toolbar toolbar;

    private ViewPager viewPager;
    private SimpleFragmentPagerAdapter viewPagerAdapter;
    private List<Tab> tabs;
    private Tab selectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");

        //---------------------------------------------create menu drawer-------------------------------------------------------

        AccountHeaderBuilder accountHeaderBuilder = createMenuDrawerHeader();
        accountHeaderBuilder.withSavedInstance(savedInstanceState);
        headerDrawer = accountHeaderBuilder.build();

        drawer = createMenuDrawer().withSavedInstance(savedInstanceState).build();
        drawer.deselect();

        tabs = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            public void onPageScrollStateChanged(int state)
            {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            public void onPageSelected(int position)
            {
                selectTab(getTabByPosition(position));
            }
        });

        //set tabs with pages
        viewPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(HomeFragment.newInstance(), "GroupFragment");
        viewPagerAdapter.addFrag(HomeFragment.newInstance(), "HomeFragment");
        viewPagerAdapter.addFrag(HomeFragment.newInstance(), "ChannelFragment");

        Tab myAppsTab = new Tab(Tab.Type.MY_APPS, findViewById(R.id.tab_groups_btn), (ImageView) findViewById(R.id.tab_my_apps_icon), (CustomTextView) findViewById(R.id.tab_my_apps_title), (ImageView) findViewById(R.id.tab_groups_line), this);
        Tab categoriesTab = new Tab(Tab.Type.CATEGORIES, findViewById(R.id.tab_items_btn), (ImageView) findViewById(R.id.tab_categories_icon), (CustomTextView) findViewById(R.id.tab_categories_title), (ImageView) findViewById(R.id.tab_items_line), this);
        Tab homeTab = new Tab(Tab.Type.HOME, findViewById(R.id.tab_dashboard_btn), (ImageView) findViewById(R.id.tab_dashboard_icon), (CustomTextView) findViewById(R.id.tab_dashboard_title), (ImageView) findViewById(R.id.tab_dashboard_line), this);

        tabs.add(categoriesTab);
        tabs.add(homeTab);
        tabs.add(myAppsTab);

        if (selectedTab == null)
            selectedTab = homeTab;

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(getTabPosition(selectedTab));

        ViewTools.setRTLSupportSettings(getWindow().getDecorView());
    }

    @Override
    public void onClick(Tab tab)
    {
        viewPager.setCurrentItem(getTabPosition(tab));
    }

    private Tab getTabByPosition(int position)
    {
        return tabs.get((tabs.size() - 1) - position);
    }

    private int getTabPosition(Tab tab)
    {
        return (tabs.size() - 1) - tabs.indexOf(tab);
    }

    private void selectTab(Tab selectedTab)
    {
        int enableColor = getResources().getColor(R.color.colorAccent);

        for (Tab tab : tabs)
        {
            if (tab.equals(selectedTab))
            {
                tab.getIcon().setColorFilter(enableColor, PorterDuff.Mode.SRC_ATOP);
                tab.getTitle().setTextColor(enableColor);
                tab.getLine().getDrawable().setColorFilter(enableColor, PorterDuff.Mode.SRC_ATOP);

                if (!tab.getType().equals(Tab.Type.HOME))
                    tab.getLine().setBackgroundColor(enableColor);
            }
            else
            {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.gray_5), PorterDuff.Mode.SRC_ATOP);
                tab.getTitle().setTextColor(getResources().getColor(R.color.gray_5));
                tab.getLine().getDrawable().setColorFilter(getResources().getColor(R.color.gray_3), PorterDuff.Mode.SRC_ATOP);

                if (!tab.getType().equals(Tab.Type.HOME))
                    tab.getLine().setBackgroundColor(getResources().getColor(R.color.gray_3));
            }
        }
    }

    public DrawerBuilder createMenuDrawer()
    {
        Typeface typeface = FontHelper.getInstance(this).getAppropriateFont();
        int color = getResources().getColor(R.color.primary);
        DrawerBuilder drawerBuilder = new DrawerBuilder(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerDrawer)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(

                        new PrimaryDrawerItem().withName(R.string.app_name).withTypeface(typeface).withIcon(new IconicsDrawable(this).color(color).icon(MaterialDesignIconic.Icon.gmi_help)).withOnDrawerItemClickListener((view, i, iDrawerItem) ->
                        {
                            drawer.deselect();
                            drawer.closeDrawer();
                            return true;
                        }),
                        new DividerDrawerItem(),

                        new PrimaryDrawerItem().withName(R.string.app_name).withTypeface(typeface).withIcon(new IconicsDrawable(this).color(color).icon(MaterialDesignIconic.Icon.gmi_settings)).withOnDrawerItemClickListener((view, i, iDrawerItem) ->
                        {
                            drawer.deselect();
                            drawer.closeDrawer();
                            return true;
                        })
                );

        return drawerBuilder;
    }

    public AccountHeaderBuilder createMenuDrawerHeader()
    {
        //User user = AppContext.getInstance().getUser();
        String fullName = "";
        IProfile profile = new ProfileDrawerItem().withEmail(fullName.equals("Test Test") ? "" : fullName);

        // Create the AccountHeader
        return new AccountHeaderBuilder()
                .withActivity(MainActivity.this)
                .withTypeface(FontHelper.getInstance(getApplicationContext()).getAppropriateFont())
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile)
                .withOnAccountHeaderSelectionViewClickListener((view, profile1) -> false);
    }
}