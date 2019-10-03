package com.appfitnessapp.app.fitnessapp.Alarma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertaRecibidaDesayuno extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationDesayuno notificationDesayuno = new NotificationDesayuno(context);
        NotificationCompat.Builder nb = notificationDesayuno.getChannelNotification();
        notificationDesayuno.getManager().notify(1, nb.build());
    }
}