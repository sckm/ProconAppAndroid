package jp.gr.procon.proconapp.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.microsoft.windowsazure.notifications.NotificationsHandler;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.ui.activity.GameResultListActivity;
import timber.log.Timber;

public class NotificationHandler extends NotificationsHandler {
    private NotificationManager mNotificationManager;
    Context context;

    @Override
    public void onReceive(Context context, Bundle bundle) {
        this.context = context;
        String nhMessage = bundle.getString("message");
        for (String key : bundle.keySet()) {
            Timber.d("onReceive key=" + key + " " + bundle.getString(key));
        }
        sendNotification(nhMessage);
    }

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                GameResultListActivity.createIntent(context, true),
                PendingIntent.FLAG_UPDATE_CURRENT);

        String appName = context.getString(R.string.app_name);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_chevron_right_black_24dp)
                        .setContentTitle(appName)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NotificationConfig.NOTIFICATION_ID_GAME_RESULT, mBuilder.build());
    }
}
