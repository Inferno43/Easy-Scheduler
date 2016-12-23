package com.ofs.ofmc.meetingroom.toolbox;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.home.HomeActivity;

import java.util.Calendar;

/**
 * Created by saravana.subramanian on 12/23/16.
 */

public class NotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.logo)
        .setAutoCancel(true)
        .addAction(android.R.drawable.ic_menu_save,"Ok, got it.",
                PendingIntent.getActivity(context, 12345,new Intent(context,HomeActivity.class),PendingIntent.FLAG_ONE_SHOT))
         .addAction(android.R.drawable.ic_menu_close_clear_cancel,
                        "Remind later",
                        PendingIntent.getBroadcast(context, 12345,new Intent(context,NotificationCancel.class),
        PendingIntent.FLAG_ONE_SHOT))
                .setContentIntent( PendingIntent.getActivity(context, 12345,
        new Intent(context,
                HomeActivity.class),
        PendingIntent.FLAG_ONE_SHOT))
                .setContentTitle("NotificaitionTesting")
                .setContentText("You got the notification from the service.");
        Notification notification = null;
        if (Build.VERSION.SDK_INT > 15) {
            notification = buildForJellyBean(builder);
        } else {
            notification = builder.build();
        }

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(123, notification);


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Notification buildForJellyBean(NotificationCompat.Builder builder1) {
        builder1.setPriority(Notification.PRIORITY_HIGH);
        return builder1.build();
    }

    public void SetAlarmR(Context context) {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR_OF_DAY, 19);
        cal1.set(Calendar.MINUTE, 55);
        cal1.set(Calendar.SECOND, 00);



        AlarmManager alarmManagerR = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent myIntentR1 = new Intent(context, NotificationPublisher.class);

        PendingIntent pendingIntentR1 = PendingIntent.getBroadcast(context, 1,
                myIntentR1, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManagerR.cancel(pendingIntentR1);

        if (Build.VERSION.SDK_INT > 19) {

            alarmManagerR.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    cal1.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                    pendingIntentR1);

        } else {

            alarmManagerR.setRepeating(AlarmManager.RTC_WAKEUP,
                    cal1.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                    pendingIntentR1);

        }
    }

    public void CancelAlarmR(Context context) {
        Intent myIntentR = new Intent(context, NotificationPublisher.class);

        PendingIntent pendingIntentR = PendingIntent.getBroadcast(context, 1,
                myIntentR, 0);

        AlarmManager alarmManagerR = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);

        alarmManagerR.cancel(pendingIntentR);

    }

}
