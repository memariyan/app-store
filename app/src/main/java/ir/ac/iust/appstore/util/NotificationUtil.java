package ir.ac.iust.appstore.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import ir.ac.iust.appstore.R;
import ir.ac.iust.appstore.model.api.Pair;

public class NotificationUtil
{

    public static final String NEW_NOTIFICATION="NEW_NOTIFICATION";
    public enum NotificationType
    {
        UNDEFINED(0),
        FRIEND_PLACE(1),
        DOWNLOAD_COMPLETE(2),
        INSTITUTE_SEIZE_REQUEST(3);

        public int value;
        NotificationType(int value)
        {
            this.value=value;
        }
    }

    private static final String BASE_NOTIFICATION_CHANNEL_ID="BASE_NOTIFICATION_CHANNEL_ID";

    public static Pair<NotificationManager, NotificationCompat.Builder> generateBaseNotification(Context context, String title, String content)
    {
        NotificationCompat.Builder builder;
        NotificationManager notificationManager;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String channelId = BASE_NOTIFICATION_CHANNEL_ID;
            CharSequence channelName = context.getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(notificationChannel);

            builder = new NotificationCompat.Builder(context, channelId);
        }
        else
        {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            builder = new NotificationCompat.Builder(context);
        }
        builder.setContentTitle(title);
        builder.setContentText(content);
        //builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_tiny));
        //builder.setSmallIcon(R.drawable.logo_tiny);

        return new Pair<>(notificationManager,builder);
    }
}
