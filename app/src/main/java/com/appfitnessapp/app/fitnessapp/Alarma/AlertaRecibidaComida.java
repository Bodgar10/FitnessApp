package com.appfitnessapp.app.fitnessapp.Alarma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertaRecibidaComida extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificacionComida notificationComida = new NotificacionComida(context);
        NotificationCompat.Builder nb = notificationComida.getChannelNotification();
        notificationComida.getManager().notify(1, nb.build());
    }

}