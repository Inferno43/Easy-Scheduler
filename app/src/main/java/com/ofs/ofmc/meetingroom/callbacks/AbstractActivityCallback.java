package com.ofs.ofmc.meetingroom.callbacks;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by saravana.subramanian on 12/1/16.
 */

public interface AbstractActivityCallback {

    void replaceActivity(Class<? extends Activity> claz, boolean addToBackStack,
                         Bundle args);
}
