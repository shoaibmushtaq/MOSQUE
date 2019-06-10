package com.example.mymosque;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static int count = 0;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

    }

    //in this method , firebase notification message will be recieved
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        getNotificationFromFirebase(remoteMessage.getData());

    }


    private void getNotificationFromFirebase(Map<String,String> notificationData){

        //initializing intent and pending intent
        Intent intent = new Intent(this,NotificationDetailScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);


        String url = notificationData.get("url");

        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("NotificationFragment", MODE_PRIVATE).edit();
        editor.putString("M_Filename", notificationData.get("file_name"));
        editor.putString("M_Filepath", notificationData.get("file_path"));
        editor.putString("M_Description_", notificationData.get("discription"));
        editor.putString("M_Type", notificationData.get("type"));
        editor.putString("M_Time", notificationData.get("time"));
        editor.putString("N_ID", notificationData.get("n_id"));
        editor.putString("M_ID", notificationData.get("m_id"));
        editor.apply();

        Log.d(TAG, "Notification Message urrrrl: " + url);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_mosque))
                .setSmallIcon(R.drawable.ic_mosque)
                .setContentTitle("My Mosque")
                .setContentText("Notification From My Mosque")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(count, notificationBuilder.build());

        count++;


    }





}
