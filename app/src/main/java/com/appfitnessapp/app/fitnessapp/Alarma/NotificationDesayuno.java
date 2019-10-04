package com.appfitnessapp.app.fitnessapp.Alarma;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.appfitnessapp.app.fitnessapp.Login.SplashPantalla;
import com.appfitnessapp.app.fitnessapp.R;

public class NotificationDesayuno extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;

    public NotificationDesayuno(Context base) {
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
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        Intent intent1 = new Intent(this.getApplicationContext(), SplashPantalla.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Recordatorio desayuno")
                .setContentText("Es hora de revisar tu desayuno.")
                .setContentIntent(pIntent)
                .setSmallIcon(R.drawable.ic_notificacion);
    }
}