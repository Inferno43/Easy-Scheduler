package com.ofs.ofmc.meetingroom.callbacks;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by saravana.subramanian on 11/29/16.
 */

public interface AbstractFragmentCallback {

    void replaceFragment(Class<? extends Fragment> claz, boolean addToBackStack,
                         Bundle args);
}
