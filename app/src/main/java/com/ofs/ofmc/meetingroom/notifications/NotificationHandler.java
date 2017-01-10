package com.ofs.ofmc.meetingroom.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.login.LoginActivity;
import com.ofs.ofmc.meetingroom.model.Schedule;
import com.ofs.ofmc.meetingroom.toolbox.Constants;

/**
 * Created by saravana.subramanian on 12/26/16.
 */

public class NotificationHandler {
    // Notification handler singleton
    private static NotificationHandler nHandler;
    private static NotificationManager mNotificationManager;
    private Schedule schedule;




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
    public void createSimpleNotification(Context context,Intent intent) {
        // Creates an explicit intent for an Activity
        Intent resultIntent = new Intent(context, LoginActivity.class);
        schedule = intent.getParcelableExtra(Constants.EXTRA);

        // Creating a artifical activity stack for the notification activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(LoginActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        // Pending intent to the notification manager
        PendingIntent resultPending = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Building the
        try{
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.logo) // notification icon
                    .setContentTitle("Hey "+schedule.getmBookieName()+" you have a schedule") // main title of the notification
                    .setContentText(schedule.getmMeetingType()+" @ "+
                            schedule.getmMeetingRoomName()+" on "+schedule.getmMeetingStartTime()) // notification text
                    .setContentIntent(resultPending); // notification intent

            // mId allows you to update the notification later on.
            mNotificationManager.notify(10, mBuilder.build());
        }catch(Exception e){
            if(Build.VERSION.SDK_INT>=24) {
                notificationException(context, intent);
            }else Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();

        }

    }

    public void notificationException(Context context,Intent intent){
        Intent resultIntent = new Intent(context, LoginActivity.class);

      //  Bundle bundle = intent.getBundleExtra(Constants.EXTRA_BUNDLE);

        // Creating a artifical activity stack for the notification activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(LoginActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        // Pending intent to the notification manager
        PendingIntent resultPending = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);



//        // Building the
//        try{
//            byte[] bytes = intent.getByteArrayExtra(Constants.EXTRA_DATA);
//            Schedule schedule = ParcelableUtils.unmarshall(bytes, Schedule.CREATOR);
//
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
//                    .setSmallIcon(R.drawable.logo) // notification icon
//                    .setContentTitle("Hey "+schedule.getmBookieName()+" you have a schedule 1") // main title of the notification
//                    .setContentText(schedule.getmMeetingType()+" @ "+
//                            schedule.getmMeetingRoomName()+" on "+schedule.getmMeetingStartTime()) // notification text
//                    .setContentIntent(resultPending); // notification intent
//
//            // mId allows you to update the notification later on.
//            mNotificationManager.notify(10, mBuilder.build());
//        }catch(Exception e){
//
//            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
//
//        }

        // Building the
        try{
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.logo) // notification icon
                    .setContentTitle("Hey  you have a schedule ahead") // main title of the notification
                    .setContentText("please launch OFS app to know more") // notification text
                    .setContentIntent(resultPending); // notification intent

            // mId allows you to update the notification later on.
            mNotificationManager.notify(10, mBuilder.build());
        }catch(Exception e){

            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();

        }
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