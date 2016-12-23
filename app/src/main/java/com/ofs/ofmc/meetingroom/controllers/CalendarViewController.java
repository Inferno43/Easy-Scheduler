package com.ofs.ofmc.meetingroom.controllers;


import android.view.View;

import com.ofs.ofmc.meetingroom.ViewMvc;

import java.util.Date;

/**
 * Created by saravana.subramanian on 12/23/16.
 */

public interface CalendarViewController extends ViewMvc {
    interface ClickListener{
        void setOnClickListener(View view);
        void setOnDateChooseListener(Date date);
    }

    void setListener(ClickListener clickListener);
}
