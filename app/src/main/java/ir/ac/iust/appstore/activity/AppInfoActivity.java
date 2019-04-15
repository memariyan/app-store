package ir.ac.iust.appstore.activity;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.fragment.CategoryFragment;
import ir.ac.iust.appstore.fragment.HomeFragment;
import ir.ac.iust.appstore.fragment.MyAppsFragment;
import ir.ac.iust.appstore.fragment.SliderImageFragment;
import ir.ac.iust.appstore.model.AppContext;
import ir.ac.iust.appstore.model.Comment;
import ir.ac.iust.appstore.view.ViewTools;
import ir.ac.iust.appstore.view.adapter.ApplicationGroupAdapter;
import ir.ac.iust.appstore.view.adapter.ApplicationImageAdapter;
import ir.ac.iust.appstore.view.adapter.CommentAdapter;
import ir.ac.iust.appstore.view.adapter.HorizontalApplicationAdapter;
import ir.ac.iust.appstore.view.adapter.SimpleFragmentPagerAdapter;

public class AppInfoActivity extends CustomAppCompatActivity
{
    private Toolbar toolbar;
    private RecyclerView commentRecyclerView;
    private RecyclerView appImagesRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_app_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ViewTools.setToolbarBackButton(this, toolbar).setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setTitle("");

        List<Drawable> images = new ArrayList<>();
        images.add(getResources().getDrawable(R.drawable.instagram_4));
        images.add(getResources().getDrawable(R.drawable.instagram_3));
        images.add(getResources().getDrawable(R.drawable.instagram_2));
        images.add(getResources().getDrawable(R.drawable.instagram_1));

        ApplicationImageAdapter horizontalApplicationAdapter = new ApplicationImageAdapter(images);

        appImagesRecyclerView=findViewById(R.id.app_images_recycler_view);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) appImagesRecyclerView.getLayoutParams();
        layoutParams.height=AppContext.getInstance().getScreenSize().getMetrics().heightPixels*3/4;
        appImagesRecyclerView.setLayoutParams(layoutParams);
        appImagesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        appImagesRecyclerView.setHasFixedSize(true);
        appImagesRecyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        appImagesRecyclerView.setAdapter(horizontalApplicationAdapter);

        List<Comment> commentList = new ArrayList<>();
        commentList.add(new Comment("پارسا","1398/1/23",4.5,"دوستان برنامه عالیه. خیلی امکانات خوب و جالبی داره"));
        commentList.add(new Comment("لیلا","1397/8/18",3,"با اینکه یه جاهاییش هنوز کار داره ولی میتونه خیلی کاربردی باشه!"));
        commentList.add(new Comment("محمد","1397/7/1",2,"برنامه جالبیه اما کاستی هایی هم داره. تو بخش تماس تلفنی مخاطبا اکثرا فینگیلیشه و برنامه متوجه نمیشه و خیلی چیزای دیگه.\nممنون از زحمتتون"));
        commentList.add(new Comment("ساناز","1397/3/14",4,"برنامه خوبیه ولی چند تا باگ جزیی داره هنوز"));
        commentList.add(new Comment("سعید","1397/3/3",1,"برنامه اصلا برای من باز نشد :("));

        CommentAdapter commentAdapter = new CommentAdapter(commentList);
        commentRecyclerView = (RecyclerView) findViewById(R.id.comments_recycler_view);
        commentRecyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        commentRecyclerView.setAdapter(commentAdapter);



    }
}
