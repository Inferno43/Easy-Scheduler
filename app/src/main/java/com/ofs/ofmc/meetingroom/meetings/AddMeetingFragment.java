package com.ofs.ofmc.meetingroom.meetings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ofs.ofmc.meetingroom.BaseFragment;
import com.ofs.ofmc.meetingroom.controllers.CalendarViewController;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.Logr;
import com.ofs.ofmc.meetingroom.toolbox.ViewUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by saravana.subramanian on 11/30/16.
 */

public class AddMeetingFragment extends BaseFragment implements CalendarViewController.ClickListener{

    CalendarViewController addMeetingView;
    private String dateString;
    Calendar today;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND,0);
        addMeetingView = new AddMeetingImplView(inflater,container);
        addMeetingView.setListener(this);

        return addMeetingView.getRootView();
    }

    @Override
    public void setOnClickListener(View view) {

    }

    @Override
    public void setOnDateChooseListener(Date date) {
        Logr.d(date.getTime()+" "+ today.getTimeInMillis());
        if(date.getTime() >= today.getTimeInMillis()){
            dateString = date.toString();
            Toast.makeText(getActivity(),ViewUtils.getInstance().getFormattedTime(date, ViewUtils.DateFormat.FORMAT2),Toast.LENGTH_LONG).show();
            Snackbar.make(addMeetingView.getRootView(), ViewUtils.getInstance().getFormattedTime(date, ViewUtils.DateFormat.FORMAT2),
                    Snackbar.LENGTH_SHORT).show();
            Bundle args = new Bundle();
            args.putString(Constants.EXTRA_DATE,dateString);
            replaceFragment(BookMeetingRoomFragment.class,false,args);

        }else{
            Snackbar.make(addMeetingView.getRootView(), "Please choose a future date",
                    Snackbar.LENGTH_SHORT).show();
        }

    }


}
