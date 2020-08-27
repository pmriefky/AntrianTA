package com.example.splashscreen.utils;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.splashscreen.HomeFragmen;
import com.example.splashscreen.MainActivity;
import com.example.splashscreen.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppReceiver extends BroadcastReceiver {
    private PendingIntent pendingIntent;
    private  static final int ALARM_REQUEST_CODE = 134;
    //set interval 10 detik
    private int interval_seconds = 10;
    private NotificationManager alarmNotificationManager;
    String NOTIFICATION_CHANNEL_ID = "resupe_channel_id";
    String NOTIFICATION_CHANNEL_NAME = "resupe channel";
    private int NOTIFICATION_ID = 1;
    Context _context;
    Intent _intent;

    @Override
    public void onReceive(Context context, Intent intent) {
        _context = context;
        _intent = intent;
        Intent alarmIntent = new Intent(context, AppReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE, alarmIntent, 0);
        //set waktu sekarang berdasarkan intervall
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date = new Date();
                    String waktu = dateFormat.format(date);
                    Log.i("jam : ", waktu);
                    if(waktu.equals("17:59:00")){
                        sendNotification(_context, _intent);
                    }
                }
            }
        }.start();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, interval_seconds);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //set alarm manager dengan memasukkan waktu yang telah dikonversikan menjadi milliseconds
        if (Build.VERSION.SDK_INT >= 23) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        //kirim notifikasi
    }

    private void sendNotification(Context context, Intent intent) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        String datetimex = sdf.format(new Date());
        String notif_title = "Coba AlarmManager Notif";
        String notif_content = "Notif time"+datetimex;
        alarmNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent newIntent = new Intent(context, HomeFragmen.class);
        newIntent.putExtra("notifkey","notifvalue");
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //cek jika OS android Oreo atau lebih baru
        //kalau tidak di set maka notifikasi tidak akan muncul di OS tersebut
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            alarmNotificationManager.createNotificationChannel(notificationChannel);
        }
        //Buat notification
        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        alamNotificationBuilder.setContentTitle(notif_title);
        alamNotificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        alamNotificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        alamNotificationBuilder.setContentText(notif_content);
        alamNotificationBuilder.setAutoCancel(true);
        alamNotificationBuilder.setContentIntent(contentIntent);
        //Tampilkan notifikasi
        alarmNotificationManager.notify(NOTIFICATION_ID, alamNotificationBuilder.build());;
    }
}
