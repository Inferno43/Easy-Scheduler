package com.ofs.ofmc.meetingroom.toolbox;


import android.util.Log;

import com.ofs.ofmc.meetingroom.BuildConfig;


/**
 * Created by saravana.subramanian on 11/30/16.
 *
 * Simplified logger to use across the app
 */

public final class Logr {
    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Log.d("Meeting Room", message);
        }
    }

    public static void d(String message, Object... args) {
        if (BuildConfig.DEBUG) {
            d(String.format(message, args));
        }
    }
}
