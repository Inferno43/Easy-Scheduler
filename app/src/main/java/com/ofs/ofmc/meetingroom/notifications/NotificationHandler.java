package com.ofs.ofmc.meetingroom.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.login.LoginActivity;

/**
 * Created by saravana.subramanian on 12/26/16.
 */

public class NotificationHandler {
    // Notification handler singleton
    private static NotificationHandler nHandler;
    private static NotificationManager mNotificationManager;


    private NotificationHandler () {}


    /**
     * Singleton pattern implementation
     * @return
     */
    public static  NotificationHandler getInstance(Context context) {
        if(nHandler == null) {
            nHandler = new NotificationHandler();
            mNotificationManager =
                    (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return nHandler;
    }


    /**
     * Shows a simple notification
     * @param context aplication context
     */
    public void createSimpleNotification(Context context) {
        // Creates an explicit intent for an Activity
        Intent resultIntent = new Intent(context, LoginActivity.class);

        // Creating a artifical activity stack for the notification activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(LoginActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        // Pending intent to the notification manager
        PendingIntent resultPending = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Building the notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo) // notification icon
                .setContentTitle("I'm a simple notification") // main title of the notification
                .setContentText("I'm the text of the simple notification") // notification text
                .setContentIntent(resultPending); // notification intent

        // mId allows you to update the notification later on.
        mNotificationManager.notify(10, mBuilder.build());
    }


//    public void createExpandableNotification (Context context) {
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            // Building the expandable content
//            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//            String lorem = context.getResources().getString(R.string.long_lorem);
//            String [] content = lorem.split("\\.");
//
//            inboxStyle.setBigContentTitle("This is a big title");
//            for (String line : content) {
//                inboxStyle.addLine(line);
//            }
//
//            // Building the notification
//            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
//                    .setSmallIcon(R.drawable.logo) // notification icon
//                    .setContentTitle("Expandable notification") // title of notification
//                    .setContentText("This is an example of an expandable notification") // text inside the notification
//                    .setStyle(inboxStyle); // adds the expandable content to the notification
//
//            mNotificationManager.notify(11, nBuilder.build());
//
//        } else {
//            Toast.makeText(context, "Can't show", Toast.LENGTH_LONG).show();
//        }
//    }



}