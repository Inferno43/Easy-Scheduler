package com.ofs.ofmc.meetingroom.schedule;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.ofs.ofmc.meetingroom.BaseFragment;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.ViewController;

import java.util.Calendar;
import java.util.List;

/**
 * Created by saravana.subramanian on 12/13/16.
 */

public class TodayTimelineFragment extends BaseFragment  implements ViewController.ClickListener{

    ViewController todayTimelineView;
    Bundle fragmentArgs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try{
            fragmentArgs = getArguments();
        }catch (Exception e){
            fragmentArgs = null;
        }
        todayTimelineView = new TodayTimelineImplView(inflater, container,fragmentArgs);
        todayTimelineView.setListener(this);



        return todayTimelineView.getRootView();
    }

    @Override
    public void setOnClickListener(View view) {

    }


}
