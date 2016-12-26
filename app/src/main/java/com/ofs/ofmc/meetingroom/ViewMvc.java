package com.ofs.ofmc.meetingroom;

import android.os.Bundle;
import android.view.View;

/**
 * Created by saravana.subramanian on 11/29/16.
 *
 * Default View Contract
 */

public interface ViewMvc {


    /**
     *
     * @return View
     */
    View getRootView();

    /**
     *
     * @return Bundle/viewstate
     */
    Bundle getViewState();


}
