package com.example.paindiary;

//Ref: //Ref: https://codinginflow.com/tutorials/android/alarmmanager
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "Channel ID";
    public static final String channelName = "Channel Name";
    private NotificationManager man;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager() {
        if (man == null) {
            man = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return man;
    }
    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Notification")
                .setContentText("Please enter your pain record !!")
                .setSmallIcon(R.drawable.notification_icon);
    }
}
