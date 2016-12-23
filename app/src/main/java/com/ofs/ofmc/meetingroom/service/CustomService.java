package com.ofs.ofmc.meetingroom.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ofs.ofmc.meetingroom.toolbox.NotificationPublisher;

/**
 * Created by saravana.subramanian on 12/23/16.
 */

public class CustomService extends Service
{
    Boolean Noti_flag;
    NotificationPublisher alarm = new NotificationPublisher();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
            alarm.SetAlarmR(this);


        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
