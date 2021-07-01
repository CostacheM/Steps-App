package com.example.stepsyscounter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class BackgroundService extends Service {
    private final LocalBinder _binder = new LocalBinder();
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager _notifyManager;
    private int _steps;

    protected Handler eventHandler = new Handler();

    public BackgroundService(){

    }
    public class LocalBinder extends Binder {
        public BackgroundService getService() {
            return BackgroundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return _binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel1();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        eventHandler.post(eventController);
        _steps = intent.getIntExtra("steps", 10);
        return android.app.Service.START_STICKY;
    }

    public void startMyOwnForeground()
    {
        Notification notification = getNotificationBuilder("App is running", "Updates").setOngoing(true)
                .build();
        startForeground(-1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, MainActivity.class);
        this.sendBroadcast(broadcastIntent);
    }

    private Runnable eventController = new Runnable()
    {
        public void run()
        {
            check();
            eventHandler.postDelayed(this, 2000);
        }
    };

    public void check(){
        if(_steps == 100) {
            sendNotification("100", "100", 2);
        }
        if(_steps == 200) {
            sendNotification("200", "200", 2);
        }
        if(_steps == 300) {
            sendNotification("300", "300", 2);
        }
        if(_steps == 500) {
            sendNotification("500", "500", 2);
        }
        if(_steps == 1000) {
            sendNotification("1000", "1000", 2);
        }
        if(_steps == 2000) {
            sendNotification("2000", "2000", 2);
        }
        if(_steps == 5000) {
            sendNotification("5000", "5000", 2);
        }
        if(_steps == 10000) {
            sendNotification("10000", "10000", 2);
        }
        if(_steps >= 20000) {
            sendNotification("20000", "20000", 2);
        }
        if(_steps >= 50000) {
            sendNotification("50000", "50000", 2);
        }
        if(_steps >= 100000) {
            sendNotification("100000", "100000", 2);
        }
    }

    public void createNotificationChannel1()
    {
        _notifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Notification", NotificationManager
                    .IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification");
            _notifyManager.createNotificationChannel(notificationChannel);
        }


    }

    private NotificationCompat.Builder getNotificationBuilder(String title, String text)
    {
        return new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle(text + " steps achievement unlocked!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp);
    }

    public void sendNotification(String title, String text, int id) {
        Intent updateIntent = new Intent(this, MainActivity.class);
        PendingIntent updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(title, text);
        notifyBuilder.addAction(R.drawable.ic_notifications_black_24dp, "Open", updatePendingIntent);
        _notifyManager.notify(id, notifyBuilder.build());
    }
}
