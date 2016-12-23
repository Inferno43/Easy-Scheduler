package com.ofs.ofmc.meetingroom.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofs.ofmc.meetingroom.BaseFragment;
import com.ofs.ofmc.meetingroom.controllers.CalendarViewController;
import com.ofs.ofmc.meetingroom.meetings.MeetingActivity;
import com.ofs.ofmc.meetingroom.schedule.ScheduleActivity;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.SharedPref;
import com.ofs.ofmc.meetingroom.toolbox.ViewUtils;


import java.util.Date;

/**
 * Created by saravana.subramanian on 11/30/16.
 */

public class MyCalendarFragment extends BaseFragment implements CalendarViewController.ClickListener{

    CalendarViewController myCalendarView;
    private String dateString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myCalendarView = new MyCalendarImplView(inflater,container);
        myCalendarView.setListener(this);


        return myCalendarView.getRootView();
    }

    @Override
    public void setOnClickListener(View view) {
        Bundle args = new Bundle();
        args.putString(Constants.FROM, Constants.MY_CALENDAR);
        args.putString(Constants.EXTRA_DATE,dateString);

        replaceActivity(MeetingActivity.class,true,args);
    }

    @Override
    public void setOnDateChooseListener(Date date) {

        dateString = date.toString();
        Snackbar.make(myCalendarView.getRootView(), ViewUtils.getInstance().getFormattedTime(date, ViewUtils.DateFormat.FORMAT2),
                Snackbar.LENGTH_SHORT).show();
        Bundle args = new Bundle();
        args.putString(Constants.FROM, Constants.MY_CALENDAR);
        args.putString(Constants.EXTRA_DATE,dateString);

        replaceActivity(ScheduleActivity.class,false,args);
    }

}
