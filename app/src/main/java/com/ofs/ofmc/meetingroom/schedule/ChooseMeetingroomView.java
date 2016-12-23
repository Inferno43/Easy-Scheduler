package com.ofs.ofmc.meetingroom.schedule;

import android.view.View;

import com.ofs.ofmc.meetingroom.ViewMvc;
import com.ofs.ofmc.meetingroom.model.MeetingRoom;

import java.util.Date;

/**
 * Created by saravana.subramanian on 12/8/16.
 */

public interface ChooseMeetingroomView extends ViewMvc {


    interface ClickListener{
        void setOnClickListener(View view);

        void setOnListItemChooseListener(MeetingRoom meetingRoom, int position);
    }

    void setListener(ClickListener clickListener);
}
