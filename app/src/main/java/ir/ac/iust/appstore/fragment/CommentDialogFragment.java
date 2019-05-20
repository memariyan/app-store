package ir.ac.iust.appstore.fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.security.KeyStore;

import javax.crypto.Cipher;

import androidx.core.app.ActivityCompat;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.activity.AppInfoActivity;
import ir.ac.iust.appstore.communication.AppStoreWS;
import ir.ac.iust.appstore.model.Application;
import ir.ac.iust.appstore.model.Comment;
import ir.ac.iust.appstore.model.api.BaseTaskHandler;
import ir.ac.iust.appstore.view.widget.CustomEditText;
import ir.ac.iust.appstore.view.widget.CustomTextView;

public class CommentDialogFragment extends DialogFragment
{
    private View submitBtn;
    private CustomEditText commentTextEdit;
    private RatingBar ratingBar;
    private Application application;

    public static CommentDialogFragment newInstance()
    {
        return new CommentDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView= inflater.inflate(R.layout.layout_comment_dialog, container, false);

        commentTextEdit = rootView.findViewById(R.id.comment_text_edit);
        ratingBar = rootView.findViewById(R.id.comment_rating_bar);
        submitBtn = rootView.findViewById(R.id.submit_comment_btn);
        submitBtn.setOnClickListener(view->
        {
            if(!commentTextEdit.getContentText().equals(""))
            {
                Comment comment =new Comment();
                comment.setApplication(application);
                comment.setRate(ratingBar.getRating());
                comment.setText(commentTextEdit.getContentText());

                AppStoreWS.getInstance().saveComment(comment, new BaseTaskHandler()
                {
                    @Override
                    public void onSuccess(Object... results)
                    {
                        dismiss();

                        Intent intent = new Intent();
                        intent.setAction(AppInfoActivity.RELOAD_APPLICATION_ACTION);
                        getActivity().sendBroadcast(intent);

                        Toast.makeText(getActivity(),"نظر شما با موفقیت ثبت شد!",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(String reason)
                    {
                        Toast.makeText(getActivity(),"متاسفانه عملیات با خطا مواجه شد!",Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                commentTextEdit.setError("لطفا دیدگاه خود را نسبت به این برنامه ثبت کنید.");
            }
        });
        return rootView;
    }

    public Application getApplication()
    {
        return application;
    }

    public void setApplication(Application application)
    {
        this.application = application;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }
}
