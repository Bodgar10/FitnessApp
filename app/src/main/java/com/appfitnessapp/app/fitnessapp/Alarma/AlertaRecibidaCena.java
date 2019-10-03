package com.appfitnessapp.app.fitnessapp.Alarma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertaRecibidaCena  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificacionCena notificationCena = new NotificacionCena(context);
        NotificationCompat.Builder nb = notificationCena.getChannelNotification();
        notificationCena.getManager().notify(1, nb.build());
    }

}