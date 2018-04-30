package com.ncl.team20.seatonvalley.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.res.ResourcesCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.activities.MainActivity;

import java.util.Random;


/**
 * Service that gets a notification from the Firebase API.<p>
 * Last Edit: 17/04/2018 by Stelios Ioannou <p>
 * @author Stelios Ioannou
 * @since 23/02/2018
 * @see com.google.firebase.messaging.FirebaseMessagingService
 * @see MainActivity
 * @see RemoteMessage
 *
 */
@SuppressWarnings("ConstantConditions")
public class SeatonFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {


    private final String ADMIN_CHANNEL_ID = "sv";

    /**
     * When a messaged is received, displays the notification.
     * @param remoteMessage the message that was sent from the Firebase Messaging Service
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        // Builds a Notifications Manager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Setting up Notification channels for android O and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels();
        }

        int notificationId = new Random().nextInt(60000);
        String title = "Seaton Valley Council";
        // Notification Content
        String content = remoteMessage.getNotification().getBody();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Notification
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Build a specifiy notification for Android O devices and above.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_stat)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null))
                    .setSound(defaultSoundUri);
            // Displays Notification
            notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
        } else {
            // Builds Notifications for Devices that use firmware Android O and below.
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_stat)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null))
                    .setSound(defaultSoundUri);
            // Displays Notification
            notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
        }
    }

    /**
     * Creates a notification channel for Android O and above.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        CharSequence adminChannelName = getString(R.string.notifications_admin_channel_name);
        String adminChannelDescription = getString(R.string.notifications_admin_channel_description);

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}

