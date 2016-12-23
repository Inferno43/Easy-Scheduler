package com.ofs.ofmc.meetingroom.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.CalendarViewController;
import com.ofs.ofmc.meetingroom.model.Profile;
import com.ofs.ofmc.meetingroom.model.Schedule;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by saravana.subramanian on 11/30/16.
 */

public class MyCalendarImplView implements CalendarViewController {


    private View mRootView;
    private ClickListener mClickListener;
    private CalendarPickerView calendarPickerView;
    private FloatingActionButton requestMeetingRoom;

    Profile profile;
    Realm realm;

    private List<Date> myScheduleDates;

    MyCalendarImplView(){}

    MyCalendarImplView(LayoutInflater inflater, ViewGroup container){

        mRootView = inflater.inflate(R.layout.mvc_view_calendar, container, false);
        calendarPickerView = (CalendarPickerView) mRootView.findViewById(R.id.calendar_view);
        requestMeetingRoom = (FloatingActionButton) mRootView.findViewById(R.id.addMeeting);

        realm = Realm.getDefaultInstance();

        profile = realm.where(Profile.class).findFirst();
        myScheduleDates = new ArrayList<>();

        if(profile!=null){
            RealmResults<Schedule> myScheduleList = realm.where(Schedule.class)
                    .contains("mBookieName",profile.getmEmployeename()).findAll();
            if(myScheduleList.size()>0){
                for(int i=0;i<myScheduleList.size();i++){
                    myScheduleDates.add(new Date(myScheduleList.get(i).getmDate()));
                }
            }else{
                myScheduleDates.add(new Date());
            }


        }

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);

        calendarPickerView.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                .withHighlightedDates(myScheduleDates)
                .withSelectedDate(new Date());

        requestMeetingRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener!=null)
                    mClickListener.setOnClickListener(view);
            }
        });

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
    public void setListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }
}
