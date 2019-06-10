package com.example.mymosque.Fragments;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mymosque.MainActivity;
import com.example.mymosque.R;

import java.util.Calendar;
import java.util.Date;

public class MyAlarm extends BroadcastReceiver {
    private NotificationManager alarmNotificationManager;
    String  Azan,RestoredAzanText,Alramchecking;
    private MediaPlayer mediaPlayer;
    Date currentTime = Calendar.getInstance().getTime();

    //Notification ID for Alarm
    public static final int NOTIFICATION_ID = 1;

    //the method will be fired when the alarm is triggerred
    @Override
    public void onReceive(Context context, Intent intent) {
        String azan_name = null;
        int j=intent.getIntExtra("azan name",1);
        switch(j){
            case 0:
                azan_name="fajar";
                break;
            case 1:
                azan_name="zuhur";
                break;
            case 2:
                azan_name="Asar";
                break;
            case 3 :
                azan_name="Maghrib";
                break;
            case 4:
                azan_name="Isha";
                break;

        }

        //you can check the log that it is fired
        //Here we are actually not doing anything
        //but you can do any task here that you want to be done at a specific time everyday
        alarmNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //get pending intent
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,new Intent(context, FragmentSecond.class), PendingIntent.FLAG_UPDATE_CURRENT);

       /* Intent cancelAudioBtn = new Intent();
        cancelAudioBtn.setAction("CANCEL_AUDIO");
        PendingIntent cancelAudioIntent = PendingIntent.getBroadcast(this,1231,cancelAudioBtn,PendingIntent.FLAG_UPDATE_CURRENT);

*/

        //Create notification
        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                context).setContentTitle("azan-e-"+azan_name).setSmallIcon(R.drawable.mosque_icon).setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.mosque_icon))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(azan_name))

                .setContentText("Alarm notification").setAutoCancel(false);
        alamNotificationBuilder.setContentIntent(contentIntent);
        // alamNotificationBuilder.addAction(R.drawable.ic_pause_white_48dp,"Stop",cancelAudioIntent);


        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("1", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);

            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert alarmNotificationManager != null;
            alamNotificationBuilder.setChannelId("1");
            alarmNotificationManager.createNotificationChannel(notificationChannel);


        }




        mediaPlayer = MediaPlayer.create(context, R.raw.azan_in_islam);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();


        alarmNotificationManager.notify(NOTIFICATION_ID, alamNotificationBuilder.build());

    }


    }

