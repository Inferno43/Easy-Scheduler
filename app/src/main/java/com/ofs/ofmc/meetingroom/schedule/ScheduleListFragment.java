package com.ofs.ofmc.meetingroom.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofs.ofmc.meetingroom.BaseFragment;
import com.ofs.ofmc.meetingroom.model.MeetingRoom;
import com.ofs.ofmc.meetingroom.model.Schedule;
import com.ofs.ofmc.meetingroom.toolbox.Constants;

/**
 * Created by saravana.subramanian on 12/8/16.
 */

public class ScheduleListFragment extends BaseFragment implements ScheduleListView.ClickListener{

    ScheduleListView scheduleListView;
    Bundle fragmentArgs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try{
            fragmentArgs = getArguments();
        }catch (Exception e){
            fragmentArgs = null;
        }

        scheduleListView = new ScheduleListImplView(inflater, container,fragmentArgs);
        scheduleListView.setListener(this);

        return scheduleListView.getRootView();
    }
    @Override
    public void setOnClickListener(View view) {

    }

    @Override
    public void setOnListItemChooseListener(Schedule schedule) {

    }


}
