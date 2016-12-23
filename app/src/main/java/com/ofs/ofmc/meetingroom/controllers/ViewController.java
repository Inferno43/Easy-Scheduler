package com.ofs.ofmc.meetingroom.controllers;

import android.view.View;

import com.ofs.ofmc.meetingroom.ViewMvc;

/**
 * Created by saravana.subramanian on 12/23/16.
 */

public interface ViewController extends ViewMvc {

    interface ClickListener{
        void setOnClickListener(View view);
    }

    void setListener(ClickListener clickListener);
}
