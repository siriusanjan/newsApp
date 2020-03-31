package com.zeptune.nepali_swipe_news.fcm_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zeptune.nepali_swipe_news.BuildConfig;
import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.test_news_pakages.TestMainActivity;

import java.util.Map;



public class NotificationSerivces extends FirebaseMessagingService {
    public static final String DEFAULT_CHANNEL_ID = "default";
    public static final String FCM_TYPE_LIVE_CHAT = "livechat";
    public static final String KEY_LIVE_CHAT_MESSAGE = "message";
    public static final String KEY_FROM_NOTIFICATION = "from_notification";

    @Override
    public void onCreate() {
        super.onCreate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Default Notification Channel");
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        /**
         * Notification received here are of following types:
         * 1. Live chat message notification
         * 2. Simple notification
         **/

        if (BuildConfig.DEBUG) {
            Log.e("FCM", "onMessageReceived: ");
        }
        Map<String, String> dataKV = remoteMessage.getData();
        Intent intent = null;
        int notificationId = (int) (Math.random() * 1000);

        intent = new Intent(this, TestMainActivity.class);
        try {
//            notificationId = Integer.parseInt(singleMessageModel.senderID);
        } catch (Exception e) {
            notificationId = (int) (Math.random() * 1000);
        }

        if (intent != null) {
            intent.putExtra(KEY_FROM_NOTIFICATION, true);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationId, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                    .setContentTitle(dataKV.get("title"))
                    .setContentText(dataKV.get("text"))
                    .setSmallIcon(R.drawable.ic_archive_black_24dp)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setOngoing(false)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_archive_black_24dp))
                    .setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) manager.notify(notificationId, builder.build());
        }

    }

}
