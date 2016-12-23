package com.ofs.ofmc.meetingroom.schedule;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.model.Schedule;
import com.ofs.ofmc.meetingroom.schedule.adapter.ScheduleAdapter;
import com.ofs.ofmc.meetingroom.toolbox.Constants;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by saravana.subramanian on 12/8/16.
 */

public class ScheduleListImplView implements ScheduleListView {

    private View mRootView;
    private Context context;
    private ClickListener mClickListener;
    private RecyclerView scheduleListView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ScheduleAdapter mAdapter;

    private Realm realm;
    private RealmResults<Schedule> schedules;
    private String selectedRoom;
    private Bundle fragmentArgs;
    private TextView message;

    public ScheduleListImplView(LayoutInflater inflater, ViewGroup container,Bundle args) {
        mRootView = inflater.inflate(R.layout.mvc_view_list, container, false);
        context = getRootView().getContext();
        if(null!=args)
            fragmentArgs = args;

        realm = Realm.getDefaultInstance();
        scheduleListView = (RecyclerView) mRootView.findViewById(R.id.listView);
        message = (TextView)mRootView.findViewById(R.id.message);
        mLayoutManager = new LinearLayoutManager(getRootView().getContext());
        scheduleListView.setLayoutManager(mLayoutManager);

            try{
                if(fragmentArgs.getString(Constants.FROM).equalsIgnoreCase(Constants.MY_CALENDAR))
                    schedules = realm.where(Schedule.class).contains("mDate",fragmentArgs.getString(Constants.EXTRA_DATE)).findAll();
                else if(fragmentArgs.getString(Constants.FROM).equalsIgnoreCase(Constants.SHARED_CALENDAR)){
                    selectedRoom = fragmentArgs.getString(Constants.EXTRA_MEETING_ROOM_NAME);
                    schedules = realm.where(Schedule.class)
                            .contains("mDate",fragmentArgs.getString(Constants.EXTRA_DATE))
                            .contains("mMeetingRoomName",selectedRoom)
                            .findAll();
                }else if(fragmentArgs.getString(Constants.FROM).equalsIgnoreCase(Constants.PUBLIC_CALENDAR)){
                    schedules = realm.where(Schedule.class)
                            .contains("mDate",fragmentArgs.getString(Constants.EXTRA_DATE))
                            .findAll();
                    Snackbar.make(mRootView,"No Schedule found",Snackbar.LENGTH_LONG).show();
                }

            }catch(Exception e){

            }
        try{
            if(schedules.size()<1)
                message.setVisibility(View.VISIBLE);
            else
                message.setVisibility(View.GONE);
        }catch(Exception e){

        }



        mAdapter = new ScheduleAdapter(schedules, new ClickListener() {
            @Override
            public void setOnClickListener(View view) {

            }

            @Override
            public void setOnListItemChooseListener(Schedule schedule) {

            }
        });
        scheduleListView.setAdapter(mAdapter);

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
