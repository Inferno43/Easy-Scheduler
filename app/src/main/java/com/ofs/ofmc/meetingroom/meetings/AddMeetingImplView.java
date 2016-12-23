package com.ofs.ofmc.meetingroom.meetings;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.CalendarViewController;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by saravana.subramanian on 11/30/16.
 */

public class AddMeetingImplView implements CalendarViewController {

    private View mRootView;
    private ClickListener mClickListener;
    private CalendarPickerView calendarPickerView;
    FloatingActionButton fab;

    public AddMeetingImplView(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.mvc_view_calendar, container, false);

        calendarPickerView = (CalendarPickerView) mRootView.findViewById(R.id.calendar_view);
        fab = (FloatingActionButton)mRootView.findViewById(R.id.addMeeting);
        fab.setVisibility(View.GONE);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);

        calendarPickerView.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                .withSelectedDate(new Date());

        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                if(mClickListener!=null)
                    mClickListener.setOnDateChooseListener(date);
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });



    }


    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    @Override
    public void setListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }
}
