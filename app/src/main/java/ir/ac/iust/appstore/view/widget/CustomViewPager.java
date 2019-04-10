package ir.ac.iust.appstore.view.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.util.ColorUtil;

/**
 * Created by IT-GIS on 7/19/2016.
 */
public class CustomViewPager
{
    public enum ImageAspectRatio
    {
        RATIO_16_9,
        RATIO_SQUARE;
    }

    private Context context;
    private View rootView;
    private ViewPager viewPager;
    private List<ImageView> dots;
    private ImageView defaultImage;
    private ImageAspectRatio aspectRatio;
    private ImageButton leftArrowImageBtn;
    private ImageButton rightArrowImageBtn;
    private OnItemClickListener mOnItemClickListener;
    private LinearLayout dotsLayout;
    private ViewPager.OnPageChangeListener userPageChangeListener;
    private CountDownTimer slideShowTimer;

    public CustomViewPager(PagerAdapter adapter, ImageAspectRatio aspectRatio, Context ctx)
    {
        this.aspectRatio = aspectRatio;
        this.context = ctx;

        //set rootview
        rootView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_custom_view_pager, null);

        //set dotLayout
        dotsLayout = (LinearLayout) rootView.findViewById(R.id.custom_view_pager_dots);

        //default image view for show when no content exist
        defaultImage = (ImageView) rootView.findViewById(R.id.custom_view_pager_default_image);

        //set viewpager
        viewPager = (ViewPager) rootView.findViewById(R.id.custom_view_pager);
        viewPager.setAdapter(adapter);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                LinearLayout.LayoutParams layout = (LinearLayout.LayoutParams) rootView.getLayoutParams();

                if (CustomViewPager.this.aspectRatio.equals(ImageAspectRatio.RATIO_16_9))
                    layout.height = (rootView.getWidth() * 9) / 16;
                else
                    layout.height = rootView.getWidth();

                if(slideShowTimer!=null)
                    slideShowTimer.start();

                rootView.setLayoutParams(layout);
                rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        final GestureDetector tapGestureDetector = new GestureDetector(context, new TapGestureListener());

        viewPager.setOnTouchListener((v, event) ->
        {
            tapGestureDetector.onTouchEvent(event);
            return false;
        });
    }

    public CustomViewPager withDots()
    {
        dots = new ArrayList<>();
        dotsLayout = (LinearLayout) rootView.findViewById(R.id.custom_view_pager_dots);
        dotsLayout.setVisibility(View.VISIBLE);

        for (int i = 0; i < viewPager.getAdapter().getCount(); i++)
            notifyItemAdded(i);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                if (userPageChangeListener != null)
                    userPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position)
            {
                selectDot(position);
                if (userPageChangeListener != null)
                    userPageChangeListener.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                if (userPageChangeListener != null)
                    userPageChangeListener.onPageScrollStateChanged(state);
            }
        });
        selectDot(0);
        return this;
    }

    public void notifyItemAdded(int position)
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ImageView dot = new ImageView(context);
        dot.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_pager_dot_not_selected));
        dot.setLayoutParams(params);
        dotsLayout.addView(dot, params);
        dots.add(dot);

        //hide default image
        if (viewPager.getAdapter().getCount() != 0)
            rootView.findViewById(R.id.default_image_layout).setVisibility(View.GONE);

        getAdapter().notifyDataSetChanged();
    }

    public void notifyItemRemoved(int position)
    {
        dots.remove(position);
        dotsLayout.removeViewAt(position);

        viewPager.getAdapter().notifyDataSetChanged();

        if (viewPager.getAdapter().getCount() == 0)
            rootView.findViewById(R.id.default_image_layout).setVisibility(View.VISIBLE);
    }

    public void showDefaultImage(Drawable drawable)
    {
        rootView.findViewById(R.id.default_image_layout).setVisibility(View.VISIBLE);
        defaultImage.setImageDrawable(drawable);
    }

    public void withDefaultImage(Drawable drawable)
    {
        this.defaultImage.setImageDrawable(drawable);
    }

    public CustomViewPager withAutoSlideShow(int duration)
    {
        slideShowTimer= new CountDownTimer(Integer.MAX_VALUE, duration)
        {
            public void onTick(long millisUntilFinished)
            {
                if(viewPager.getAdapter().getCount()>0)
                    viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % viewPager.getAdapter().getCount());
            }
            public void onFinish()
            {
                slideShowTimer.start();
            }
        };
        return this;
    }

    public CustomViewPager withNavigateBtn(boolean innerBtns)
    {
        int navBtnColor;
        if (innerBtns)
        {
            rootView.findViewById(R.id.view_pager_inner_left_arrow_layout).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.view_pager_inner_right_arrow_layout).setVisibility(View.VISIBLE);
            leftArrowImageBtn = (ImageButton) rootView.findViewById(R.id.view_pager_inner_left_arrow);
            rightArrowImageBtn = (ImageButton) rootView.findViewById(R.id.view_pager_inner_right_arrow);
            navBtnColor = context.getResources().getColor(R.color.white);
        }
        else
        {
            rootView.findViewById(R.id.view_pager_outer_left_arrow_layout).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.view_pager_outer_right_arrow_layout).setVisibility(View.VISIBLE);
            leftArrowImageBtn = (ImageButton) rootView.findViewById(R.id.view_pager_outer_left_arrow);
            rightArrowImageBtn = (ImageButton) rootView.findViewById(R.id.view_pager_outer_right_arrow);
            navBtnColor = context.getResources().getColor(R.color.primary);
        }

        leftArrowImageBtn.setImageDrawable(new IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_chevron_right).color(navBtnColor).sizeDp(15));
        rightArrowImageBtn.setImageDrawable(new IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_chevron_left).color(navBtnColor).sizeDp(15));

        leftArrowImageBtn.setOnClickListener(v -> viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % viewPager.getAdapter().getCount()));
        rightArrowImageBtn.setOnClickListener(v -> viewPager.setCurrentItem((viewPager.getCurrentItem() - 1) % viewPager.getAdapter().getCount()));

        return this;
    }

    public CustomViewPager withPageChangeListener(ViewPager.OnPageChangeListener listener)
    {
        userPageChangeListener = listener;
        return this;
    }

    public View getView()
    {
        return rootView;
    }

    public void selectDot(int idx)
    {
        Resources res = context.getResources();
        for (int i = 0; i < dots.size(); i++)
        {
            Drawable drawable = (i == idx) ? ColorUtil.colorizeDrawable(context, context.getResources().getDrawable(R.drawable.icon_pager_dot_selected), R.color.colorPrimaryDark) : res.getDrawable(R.drawable.icon_pager_dot_not_selected);
            dots.get(i).setImageDrawable(drawable);
            dots.get(i).refreshDrawableState();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    private class TapGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e)
        {
            if (mOnItemClickListener != null)
            {
                mOnItemClickListener.onItemClick(viewPager.getCurrentItem());
            }
            return true;
        }
    }

    public PagerAdapter getAdapter()
    {
        return viewPager.getAdapter();
    }
}
