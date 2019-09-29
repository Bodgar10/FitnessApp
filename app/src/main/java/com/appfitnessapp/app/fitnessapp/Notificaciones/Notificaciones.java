package com.appfitnessapp.app.fitnessapp.Notificaciones;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notificaciones extends Application {

    public static final String CHANNEL_1_ID="channel1";

    public static final String CHANNEL_2_ID="channel2";




    @Override
    public void onCreate() {
        super.onCreate();


        createNotification();

    }

    private void createNotification() {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1= new NotificationChannel(
                    CHANNEL_1_ID,
                    "channel 1",
                    NotificationManager.IMPORTANCE_HIGH);

        }
    }


}
