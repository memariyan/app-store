package ir.ac.iust.appstore.communication.download;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.ProgressBar;

import java.io.File;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.model.FileDownload;
import ir.ac.iust.appstore.model.api.Pair;
import ir.ac.iust.appstore.util.NotificationUtil;

/**
 * Created by IT-GIS on 2/27/2017.
 */

public class ApkDownloadHandler implements DownloadHandler
{
    private static final int FILE_DOWNLOAD_NOTIFICATION_ID = 184;
    protected FileDownload fileDownload;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager mNotifyManager;
    private boolean showOnNotification;
    private Context context;
    private int lastProgress=0;
    private ProgressBar downloadProgressBar;

    public ApkDownloadHandler(FileDownload fileDownload, Context context, boolean showOnNotification, ProgressBar downloadProgressBar)
    {
        this.fileDownload=fileDownload;
        this.context=context;
        this.showOnNotification = showOnNotification;
        this.downloadProgressBar=downloadProgressBar;
        if (showOnNotification)
        {
            Pair<NotificationManager,NotificationCompat.Builder> notificationPair= NotificationUtil.generateBaseNotification(context,fileDownload.getViewName(),context.getString(R.string.download_status_downloading));
            mNotifyManager = notificationPair.getLeft();
            notificationBuilder = notificationPair.getRight().setSmallIcon(R.drawable.icon_download_notification);
            mNotifyManager.notify(FILE_DOWNLOAD_NOTIFICATION_ID, notificationBuilder.build());
        }
    }

    @Override
    public void onProgress(int downloaded)
    {
        int progress=(int)(((float) downloaded / (fileDownload.getSize() * 1024)) * 100);
        if(lastProgress<progress)
        {
            lastProgress=progress;
            if(showOnNotification)
            {
                notificationBuilder.setContentText("%"+lastProgress);
                notificationBuilder.setProgress(100,lastProgress, false);
                mNotifyManager.notify(FILE_DOWNLOAD_NOTIFICATION_ID, notificationBuilder.build());
            }
            downloadProgressBar.setProgress(lastProgress);
        }
    }

    @Override
    public void onSuccess(Object... results)
    {
        Intent pendingIntent;
        File apkFile = new File((String) results[0]);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            Uri fileURI= FileProvider.getUriForFile(context, "ir.ac.iust.appstore.fileprovider", apkFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(fileURI, "application/vnd.android.package-archive");
            context.startActivity(intent);

            //remove progress from notification and change status to downloaded
            pendingIntent = new Intent(Intent.ACTION_VIEW)
                    .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK)
                    .setDataAndType(fileURI, "application/vnd.android.package-archive");
        }
        else
        {
            Uri apkUri = Uri.fromFile(apkFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            //remove progress from notification and change status to downloaded
            pendingIntent =  new Intent(Intent.ACTION_VIEW)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .setDataAndType(apkUri, "application/vnd.android.package-archive");
        }

        if(showOnNotification)
        {
            notificationBuilder.setContentText(fileDownload.getViewName()+" با موفقیت دانلود شد و اکنون آماده نصب می باشد! ").setContentIntent(PendingIntent.getActivity(context, 0,pendingIntent,0)).setProgress(0,0,false);
            mNotifyManager.notify(FILE_DOWNLOAD_NOTIFICATION_ID, notificationBuilder.build());
        }
        else
            context.startActivity(pendingIntent);

    }

    @Override
    public void onFailure(String reason) {}
}
