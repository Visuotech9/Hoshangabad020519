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

//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
//
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            intent = new Intent(this, Act_notification.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        }
//
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
//
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
//
//        notificationBuilder.setAutoCancel(true)
//                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
//                .setContentTitle(remoteMessage.getData().get("title"))
//                .setContentText(remoteMessage.getData().get("body"))
//                .setDefaults(Act_notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.app_icon)
//                .setContentIntent(pendingIntent)
//                .setLights(Color.BLUE,1,1)
//                .setAutoCancel(true);
//        mNotificationManager.notify(1000, notificationBuilder.build());
//    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notificationss messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        sendNotification(remoteMessage.getData().get("message"));
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notificationss Body: " + remoteMessage.getNotification().getBody());
//            sendNotification(remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {

        Intent intent = new Intent(this, Act_notification.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);


//        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_stat_call_white);
//        Bitmap largeIcon1 = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon1);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.elections)
//                .setLargeIcon(largeIcon)
//                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            notificationBuilder.setSmallIcon(R.mipmap.ic_stat_call_white);
////            notificationBuilder.setLargeIcon(largeIcon);
//            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimary));
//        } else {
//            notificationBuilder.setSmallIcon(R.drawable.app_icon1);
////            notificationBuilder.setLargeIcon(largeIcon1);
//            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimary));
//        }


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());




        //=================================


    }
}
