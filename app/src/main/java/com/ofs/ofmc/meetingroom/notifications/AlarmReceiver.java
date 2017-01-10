package com.ofs.ofmc.meetingroom.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.home.HomeActivity;
import com.ofs.ofmc.meetingroom.model.Schedule;
import com.ofs.ofmc.meetingroom.toolbox.Constants;

/**
 * Created by saravana.subramanian on 12/26/16.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private final String ACTION = "com.alarm.ACTION";

    NotificationHandler nHandler;

    @Override
    public void onReceive(Context context, Intent intent) {
        nHandler = NotificationHandler.getInstance(context);
        String action = intent.getAction();

        if (ACTION.equals(action)) {
            //do what you want here
            nHandler.createSimpleNotification(context,intent);
        }
    }

}