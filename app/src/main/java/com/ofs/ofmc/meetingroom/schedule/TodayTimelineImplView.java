package com.ofs.ofmc.meetingroom.schedule;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.ViewController;
import com.ofs.ofmc.meetingroom.model.Schedule;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by saravana.subramanian on 12/13/16.
 */

public class TodayTimelineImplView implements ViewController, WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {

    private View mRootView;
    private ClickListener mClickListener;
    private List<WeekViewEvent> events;
    private WeekView mWeekView;
    private Realm realm;

    private RealmResults<Schedule> schedules;

    private Bundle fragmentArgs;
    private String selectedRoom;
    private String selectedDate;

    TodayTimelineImplView(LayoutInflater layoutInflater, ViewGroup container,Bundle args){

        mRootView = layoutInflater.inflate(R.layout.mvc_view_timeline_today, container, false);

        if(args!=null){
            fragmentArgs = args;
        }


        mWeekView = (WeekView) mRootView.findViewById(R.id.weekView);
        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

        mWeekView.setNumberOfVisibleDays(1);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);

        realm = Realm.getDefaultInstance();


        try{
            if(fragmentArgs.getString(Constants.FROM).equalsIgnoreCase(Constants.MY_CALENDAR)){
                selectedDate = fragmentArgs.getString(Constants.EXTRA_DATE);
                schedules = realm.where(Schedule.class).contains("mDate",fragmentArgs.getString(Constants.EXTRA_DATE)).findAll();
            }else if(fragmentArgs.getString(Constants.FROM).equalsIgnoreCase(Constants.SHARED_CALENDAR)){
                selectedRoom = fragmentArgs.getString(Constants.EXTRA_MEETING_ROOM_NAME);
                selectedDate = fragmentArgs.getString(Constants.EXTRA_DATE);
                schedules = realm.where(Schedule.class)
                        .contains("mDate",fragmentArgs.getString(Constants.EXTRA_DATE))
                        .contains("mMeetingRoomName",selectedRoom)
                        .findAll();
            }else if(fragmentArgs.getString(Constants.FROM).equalsIgnoreCase(Constants.PUBLIC_CALENDAR)){
                selectedDate = fragmentArgs.getString(Constants.EXTRA_DATE);
                Snackbar.make(mRootView,"No Schedule found",Snackbar.LENGTH_LONG).show();
            }

        }catch(Exception e){

        }
        events = new ArrayList<>();
        try{
            for(int i=0;i<schedules.size();i++){
                events.add(new WeekViewEvent( schedules.get(i).getId(),  schedules.get(i).getmMeetingType(),

                        Utils.dateVariant(selectedDate,'Y'),  Utils.dateVariant(selectedDate,'M'), Utils.dateVariant(selectedDate,'D'),
                        Utils.timeVariant(schedules.get(i).getmMeetingStartTime(),'H'),
                        Utils.timeVariant(schedules.get(i).getmMeetingStartTime(),'m'),

                        Utils.dateVariant(selectedDate,'Y'), Utils.dateVariant(selectedDate,'M'),  Utils.dateVariant(selectedDate,'D'),
                        Utils.timeVariant(schedules.get(i).getmMeetingEndTime(),'H'),
                        Utils.timeVariant(schedules.get(i).getmMeetingEndTime(),'m')));
            }
        }catch (NullPointerException E){
            E.printStackTrace();
        }

    }


    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */

    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
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

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return events;
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {

    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }

    public WeekView getWeekView() {
        return mWeekView;
    }
}
