package com.ofs.ofmc.meetingroom.toolbox;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by saravana.subramanian on 12/23/16.
 */
public class NotificationCancel extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 123;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NotificationCancel.NOTIFICATION_ID);

    }

}