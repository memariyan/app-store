package ir.ac.iust.appstore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.model.AppContext;
import ir.ac.iust.appstore.model.Category;
import ir.ac.iust.appstore.view.ViewTools;
import ir.ac.iust.appstore.view.adapter.ApplicationGroupAdapter;
import ir.ac.iust.appstore.view.adapter.CategoryItemAdapter;

public class CategoryItemsFragment extends Fragment
{
    private RecyclerView categoriesRecyclerView;

    public CategoryItemsFragment()
    {
        // Required empty public constructor
    }

    public static CategoryItemsFragment newInstance()
    {
        CategoryItemsFragment fragment = new CategoryItemsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_category_item, container, false);

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("شبکه اجتماعی",R.drawable.icon_social_network));
        categories.add(new Category("آب و هوا",R.drawable.icon_weather));

        CategoryItemAdapter categoriesAdapter = new CategoryItemAdapter(categories, new CategoryItemAdapter.CategoryItemHandler()
        {
            @Override
            public void onItemClicked(int position, Category category)
            {

            }
        });

        categoriesRecyclerView =rootView.findViewById(R.id.category_recycler_view);
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false));
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener()
                {
                    @Override
                    public void onGlobalLayout()
                    {
                        GridLayoutManager gridLayoutManager = (GridLayoutManager) categoriesRecyclerView.getLayoutManager();
                        int currentSpanCount = ((int) Math.floor(AppContext.getInstance().getScreenSize().getMetrics().widthPixels / getResources().getDimension(R.dimen.category_item_layout_width)));
                        gridLayoutManager.setSpanCount(currentSpanCount);
                        gridLayoutManager.requestLayout();

                        System.out.println(currentSpanCount+"--------------");

                        categoriesRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });

        ViewTools.setRTLSupportSettings(rootView);
        return rootView;
    }
}
