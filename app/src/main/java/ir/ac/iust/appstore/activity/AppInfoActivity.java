package ir.ac.iust.appstore.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.communication.AppStoreWS;
import ir.ac.iust.appstore.communication.download.ApkDownloadHandler;
import ir.ac.iust.appstore.communication.download.UserFileDownloader;
import ir.ac.iust.appstore.fragment.CommentDialogFragment;
import ir.ac.iust.appstore.model.AppContext;
import ir.ac.iust.appstore.model.Application;
import ir.ac.iust.appstore.model.Comment;
import ir.ac.iust.appstore.model.FileDownload;
import ir.ac.iust.appstore.model.Image;
import ir.ac.iust.appstore.model.api.BaseTaskHandler;
import ir.ac.iust.appstore.view.ViewTools;
import ir.ac.iust.appstore.view.adapter.ApplicationImageAdapter;
import ir.ac.iust.appstore.view.adapter.CommentAdapter;
import ir.ac.iust.appstore.view.widget.CustomButton;
import ir.ac.iust.appstore.view.widget.CustomTextView;

public class AppInfoActivity extends CustomAppCompatActivity
{
    public static final String APP_ID_ARG="APP_ID_ARG";
    public static final String RELOAD_APPLICATION_ACTION = "ir.ac.iust.appstore.RELOAD_APPLICATION_ACTION";


    private Toolbar toolbar;
    private RecyclerView commentRecyclerView;
    private RecyclerView appImagesRecyclerView;
    private ImageView logo;
    private ProgressBar downloadProgressBar;
    private CustomTextView appName;
    private CustomTextView developerName;
    private CustomTextView description;
    private CustomTextView downloadCount;
    private CustomTextView rate;
    private CustomTextView categoryName;
    private CustomTextView appSize;
    private CustomButton actionBtn;
    private FloatingActionButton newCommentBtn;

    private long appId;
    private Application application;
    private List<Image> images=new ArrayList<>();
    private List<Comment> comments=new ArrayList<>();
    private CommentAdapter commentAdapter;
    private ApplicationImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_app_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ViewTools.setToolbarBackButton(this, toolbar).setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setTitle("");

        newCommentBtn = findViewById(R.id.add_comment_button);
        logo = findViewById(R.id.app_icon);
        appName = findViewById(R.id.app_name);
        developerName = findViewById(R.id.developer_name);
        description = findViewById(R.id.app_description);
        downloadCount=findViewById(R.id.app_download_count);
        rate = findViewById(R.id.app_rate);
        categoryName=findViewById(R.id.app_category);
        appSize=findViewById(R.id.app_size);
        downloadProgressBar = findViewById(R.id.download_progress_bar);
        downloadProgressBar.setVisibility(View.GONE);
        actionBtn = findViewById(R.id.action_btn);
        imageAdapter = new ApplicationImageAdapter(images);

        appImagesRecyclerView=findViewById(R.id.app_images_recycler_view);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) appImagesRecyclerView.getLayoutParams();
        layoutParams.height=AppContext.getInstance().getScreenSize().getMetrics().heightPixels*3/4;
        appImagesRecyclerView.setLayoutParams(layoutParams);
        appImagesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        appImagesRecyclerView.setHasFixedSize(true);
        appImagesRecyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        appImagesRecyclerView.setAdapter(imageAdapter);

        commentAdapter = new CommentAdapter(comments);
        commentRecyclerView = (RecyclerView) findViewById(R.id.comments_recycler_view);
        commentRecyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        commentRecyclerView.setAdapter(commentAdapter);

        appId = getIntent().getLongExtra(APP_ID_ARG,0);
        if (appId != 0)
        {
            loadApplicationData();
        }

        actionBtn.setOnClickListener(view ->
        {
            if ((ContextCompat.checkSelfPermission(AppInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(AppInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
            {
                String[] permissions = new String[2];
                permissions[0]= Manifest.permission.READ_EXTERNAL_STORAGE;
                permissions[1]= Manifest.permission.WRITE_EXTERNAL_STORAGE;
                ActivityCompat.requestPermissions(AppInfoActivity.this, permissions, 1);
            }
            else
            {
                startDownload();
            }
        });

        newCommentBtn.setOnClickListener(view ->
        {
            showCommentDialog();
        });
    }

    private void loadApplicationData()
    {
        AppStoreWS.getInstance().getApplication(appId, new BaseTaskHandler()
        {
            @Override
            public void onSuccess(Object... results)
            {
                application = (Application) results[0];

                Glide.with(AppInfoActivity.this).load(application.getLogo().getUrl()).into(logo);

                String appNameText = application.getName();

                appName.setText(appNameText);
                developerName.setText(application.getDeveloper());
                description.setText(application.getDescription());
                categoryName.setText(application.getCategory().getName());
                rate.setText(String.valueOf(application.getRate()));
                appSize.setText(application.getSize()+"  Mb");

                if(application.getComments()!=null)
                {
                    comments.addAll(application.getComments());
                    commentAdapter.notifyDataSetChanged();
                }

                for(Image image : application.getImages())
                {
                    if(image.getType().equals(Image.Type.APP_IMAGE))
                    {
                        images.add(image);
                    }
                }
                imageAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(String reason)
            {

            }
        });

    }

    private void startDownload()
    {
        FileDownload fileDownload=new FileDownload(application.getName(), application.getUrl(), application.getFileName(),(int)(application.getSize()*1048), FileDownload.DownloadGroup.APK, FileDownload.Status.WAITING);
        UserFileDownloader.pushAndStartDownload(fileDownload, new CustomApkDownloadHandler(fileDownload,getApplicationContext(),false,downloadProgressBar), getApplicationContext());

        setViewDownloadMode();
    }

    private void setViewDownloadMode()
    {
        actionBtn.setEnabled(false);
        actionBtn.setText("در حال دانلود");
        downloadProgressBar.setVisibility(View.VISIBLE);
    }

    private void setViewNormalMode()
    {
        actionBtn.setEnabled(true);
        actionBtn.setText("نصب");
        downloadProgressBar.setVisibility(View.GONE);
    }

    class CustomApkDownloadHandler extends ApkDownloadHandler
    {
        public CustomApkDownloadHandler(FileDownload fileDownload, Context context, boolean showOnNotification, ProgressBar downloadProgressBar)
        {
            super(fileDownload, context, showOnNotification, downloadProgressBar);
        }

        @Override
        public void onFailure(String reason)
        {
            super.onFailure(reason);
            setViewNormalMode();
        }

        @Override
        public void onSuccess(Object... results)
        {
            super.onSuccess(results);
            setViewNormalMode();
        }
    }

    private void showCommentDialog()
    {
        CommentDialogFragment dialog = CommentDialogFragment.newInstance();
        dialog.setApplication(application);
        dialog.show(getFragmentManager(), "CommentDialog");
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RELOAD_APPLICATION_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            if (action.matches(RELOAD_APPLICATION_ACTION))
            {
                loadApplicationData();
            }
        }
    };
}
