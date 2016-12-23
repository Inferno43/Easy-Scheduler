package com.ofs.ofmc.meetingroom.schedule;

import android.view.View;

import com.ofs.ofmc.meetingroom.ViewMvc;
import com.ofs.ofmc.meetingroom.model.MeetingRoom;
import com.ofs.ofmc.meetingroom.model.Schedule;

/**
 * Created by saravana.subramanian on 12/8/16.
 */

public interface ScheduleListView extends ViewMvc{

    interface ClickListener{
        void setOnClickListener(View view);

        void setOnListItemChooseListener(Schedule schedule);
    }

    void setListener(ClickListener clickListener);
}
