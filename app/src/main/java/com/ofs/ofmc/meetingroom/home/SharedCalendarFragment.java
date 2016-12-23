package com.ofs.ofmc.meetingroom.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ofs.ofmc.meetingroom.BaseFragment;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.CalendarViewController;
import com.ofs.ofmc.meetingroom.meetings.MeetingActivity;
import com.ofs.ofmc.meetingroom.schedule.ScheduleActivity;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.ViewUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by saravana.subramanian on 12/2/16.
 */

public class SharedCalendarFragment extends BaseFragment implements CalendarViewController.ClickListener{

    CalendarViewController sharedCalendarView;
    private Context context;
    private String dateString;
    ArrayList<Date> loaddates;
    Calendar today ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        today = Calendar.getInstance();
        sharedCalendarView = new SharedCalendarImplView(inflater,container);
        sharedCalendarView.setListener(this);
        setHasOptionsMenu(false);

        return sharedCalendarView.getRootView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;

    }

    @Override
    public void setOnDateChooseListener(Date date) {

        dateString = date.toString();

        Snackbar.make(sharedCalendarView.getRootView(), ViewUtils.getInstance().getFormattedTime(date, ViewUtils.DateFormat.FORMAT2),
                Snackbar.LENGTH_SHORT).show();

        Bundle args = new Bundle();
        args.putString(Constants.FROM, Constants.SHARED_CALENDAR);
        args.putString(Constants.EXTRA_DATE,dateString);
        replaceActivity(ScheduleActivity.class,false,args);

    }

    @Override
    public void setOnClickListener(View view) {

        Bundle args = new Bundle();
        args.putString(Constants.FROM, Constants.SHARED_CALENDAR);
        args.putString(Constants.EXTRA_DATE,dateString);

        replaceActivity(MeetingActivity.class,true,args);

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);

        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {

            case R.id.alma:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.council:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.matrix:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.opera:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.C1:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.C2:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.C3:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.C4:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.C5:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.C6:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.C7:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.T1:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.T2:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
