package com.example.covid19tracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class MyFirebaseInstance extends FirebaseMessagingService {

    final String TAG = "Debug";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // handle when notification is received via data event
        if(remoteMessage.getData().size() >0){
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
        }
        // handle when receive notification
        if(remoteMessage.getNotification() != null){
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private RemoteViews getCustomDesign(String title, String message){
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.tvTitle, title);
        remoteViews.setTextViewText(R.id.tvMessage, message);
        remoteViews.setImageViewResource(R.id.ivNotification, R.mipmap.covid19_logo);

        return remoteViews;
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    public void showNotification(String title, String message){
        Intent intent = new Intent(this, MainActivity.class);
        String channel_id = "COVID19_channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setSmallIcon(R.mipmap.covid19_logo_round)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            builder = builder.setContent(getCustomDesign(title, message));
        }
        else{
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.covid19_logo_round);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "Covid19 Tracker", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0, builder.build());

    }
}
