package com.visuotech.hoshangabad_election.PushNotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.visuotech.hoshangabad_election.Activities.Act_notification;
import com.visuotech.hoshangabad_election.R;
import com.visuotech.hoshangabad_election.SessionParam;

import java.io.ByteArrayOutputStream;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    SessionParam sessionParam;
    Context context;

    /**
     * Called when message is received.
     *
     */
    // [START receive_message]
    Intent intent;
    @Override
    public void onNewToken(String s) {
        Log.e("FCM.refreshedToken", s);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN),s);
        editor.commit();

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        sendNotification(remoteMessage.getData().get("message"),remoteMessage.getData().get("title"));
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notificationss Body: " + remoteMessage.getNotification().getBody());

            String title=remoteMessage.getNotification().getTitle();
            String body=remoteMessage.getNotification().getBody();
            String click_action=remoteMessage.getNotification().getClickAction();

            Log.e("message>>>>>>>",body);
            Log.e("title>>>>>>>",title);
            Log.e("Action>>>>>>>",click_action);
            sendNotification(body, title, click_action);
        }

        //To get a Bitmap image from the URL received
//        bitmap = getBitmapfromUrl(imageUri);
        //method for functioning the notification --->


    }

    private void sendNotification(String messageBody, String title, String click_action) {
        Intent intent = new Intent(click_action);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "Default");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        notificationBuilder.setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setContentTitle(title)
                .setContentText(messageBody)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
//                .setColor(getResources().getColor(R.color.colorPrimary))
//                .setSmallIcon(R.mipmap.ic_stat_call_white)
                .setContentIntent(pendingIntent)
                .setLights(Color.BLUE,1,1)
                .setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1000, notificationBuilder.build());
    }


    }

