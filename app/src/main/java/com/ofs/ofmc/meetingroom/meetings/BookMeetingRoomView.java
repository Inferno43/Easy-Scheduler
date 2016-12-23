package com.ofs.ofmc.meetingroom.meetings;

import android.view.View;

import com.ofs.ofmc.meetingroom.ViewMvc;
import com.ofs.ofmc.meetingroom.model.Schedule;

import java.util.Date;

/**
 * Created by saravana.subramanian on 12/13/16.
 */

public interface BookMeetingRoomView extends ViewMvc {

    interface ClickListener{
        void setOnClickListener(View view);
        void onSubmitClickListener(Schedule schedule);
    }

    void setListener(ClickListener clickListener);
}
