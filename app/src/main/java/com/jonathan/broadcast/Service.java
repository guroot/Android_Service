package com.jonathan.broadcast;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static android.support.v4.app.NotificationCompat.PRIORITY_MIN;

public class Service extends android.app.Service {
    private static final String TAG = "MyService";

    public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        /*
        Obligatoire depuis SDK 26
        Selon la configuration de l'appareil, une notification
        est présentée à l'utilisateur pour qu'il prenne connaissance
        du démarrage de l'application.

        https://developer.android.com/training/notify-user/channels
        https://developer.android.com/about/versions/oreo/background

        [...]the system doesn't allow a background app to create a background service. For this reason,
        Android 8.0 introduces the new method startForegroundService() to start a new service in the
        foreground. After the system has created the service, the app has five seconds to call the
        service's startForeground() method to show the new service's user-visible notification. [...]
         */
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "Broadcast_Service_Demo";
            String CHANNEL_NAME = "My Background Service";

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setCategory(Notification.CATEGORY_SERVICE).setSmallIcon(R.drawable.ic_launcher_background).setPriority(PRIORITY_MIN).build();

            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart");
        return super.onStartCommand(intent, flags, startId);
    }
}

