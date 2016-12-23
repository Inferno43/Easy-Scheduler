package com.ofs.ofmc.meetingroom.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by saravana.subramanian on 12/23/16.
 */


public class AutoStart extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent arg1) {
        Log.d("Autostart", "BOOT_COMPLETED broadcast received. Executing starter service.");

            if (Intent.ACTION_BOOT_COMPLETED.equals(arg1.getAction())) {
                Intent intent = new Intent(context, CustomService.class);
                context.startService(intent);
            }

    }
}