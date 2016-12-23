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
import com.ofs.ofmc.meetingroom.toolbox.ViewUtils;

import java.util.Date;

/**
 * Created by saravana.subramanian on 12/2/16.
 */

public class PublicCalendarFragment extends BaseFragment implements CalendarViewController.ClickListener{

    CalendarViewController publicCalendarView;
    private String dateString;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        publicCalendarView = new PublicCalendarImplView(inflater,container);
        publicCalendarView.setListener(this);

        return publicCalendarView.getRootView();
    }

    @Override
    public void setOnDateChooseListener(Date date) {

        dateString = date.toString();
        Snackbar.make(publicCalendarView.getRootView(), ViewUtils.getInstance().getFormattedTime(date, ViewUtils.DateFormat.FORMAT2),
                Snackbar.LENGTH_SHORT).show();
        Bundle args = new Bundle();
        args.putString(Constants.FROM, Constants.PUBLIC_CALENDAR);
        args.putString(Constants.EXTRA_DATE,dateString);
        replaceActivity(ScheduleActivity.class,false,args);
    }

    @Override
    public void setOnClickListener(View view) {

        Bundle args = new Bundle();
        args.putString(Constants.FROM, Constants.PUBLIC_CALENDAR);
        args.putString(Constants.EXTRA_DATE,dateString);

        replaceActivity(MeetingActivity.class,true,args);

    }


}
