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
import com.ofs.ofmc.meetingroom.toolbox.Utils;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by saravana.subramanian on 12/2/16.
 */

public class SharedCalendarImplView implements CalendarViewController{


    private View mRootView;
    private ClickListener mClickListener;
    private CalendarPickerView calendarPickerView;

    private FloatingActionButton requestMeetingRoom;
    ArrayList<Date> myScheduleDates;
    Profile profile;

    Realm realm;
    Calendar nextYear;
    Calendar lastYear;
    SharedCalendarImplView(LayoutInflater inflater, ViewGroup container){

        mRootView = inflater.inflate(R.layout.mvc_view_calendar, container, false);
        calendarPickerView = (CalendarPickerView) mRootView.findViewById(R.id.calendar_view);
        requestMeetingRoom = (FloatingActionButton) mRootView.findViewById(R.id.addMeeting);

        Calendar today = Calendar.getInstance();
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);

        realm = Realm.getDefaultInstance();

        profile = realm.where(Profile.class).findFirst();
        myScheduleDates = new ArrayList<>();

        if(profile!=null){
            RealmResults<Schedule> myScheduleList = realm.where(Schedule.class)
                    .contains("mBookieName",profile.getmEmployeename()).findAll();
            if(myScheduleList.size()>0){
                for(int i=0;i<myScheduleList.size();i++){
                    myScheduleDates.add(Utils.toDate(myScheduleList.get(i).getmDate()));
                }
            }else{
                myScheduleDates.add(new Date());
            }


        }

        calendarPickerView.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        calendarPickerView.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                .withHighlightedDates(myScheduleDates)
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

        requestMeetingRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener!=null)
                    mClickListener.setOnClickListener(view);
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
