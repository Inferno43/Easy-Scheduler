package com.ofs.ofmc.meetingroom.schedule.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.model.Schedule;
import com.ofs.ofmc.meetingroom.schedule.ScheduleListView;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by saravana.subramanian on 12/8/16.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    ScheduleListView.ClickListener clickListener;
    private ArrayList<Schedule> schedules;
    RealmResults<Schedule> scheduleRealmResults;

    public ScheduleAdapter(ArrayList<Schedule> schedules, ScheduleListView.ClickListener clickListener) {
        this.schedules = schedules;
        this.clickListener = clickListener;
    }

    public ScheduleAdapter(RealmResults<Schedule> scheduleRealmResults, ScheduleListView.ClickListener clickListener) {
        this.scheduleRealmResults = scheduleRealmResults;
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mBookieName;
        public CardView mItemContainer;
        public TextView mBookieDesignation;
        public TextView mBookieTeam;
        public TextView mMeetingType;
        public TextView mMeetingStartTime;
        public TextView mMeetingEndTime;
        public TextView mMeetingRoomName;

        public ViewHolder(View itemView) {
            super(itemView);

            mItemContainer = (CardView) itemView.findViewById(R.id.item_container);
            mBookieName = (TextView) itemView.findViewById(R.id.name);
            mBookieDesignation = (TextView) itemView.findViewById(R.id.designation);
            mBookieTeam = (TextView) itemView.findViewById(R.id.team);
            mMeetingType = (TextView) itemView.findViewById(R.id.meetingType);
            mMeetingRoomName = (TextView) itemView.findViewById(R.id.meetingRoomName);
            mMeetingStartTime = (TextView) itemView.findViewById(R.id.startTime);
            mMeetingEndTime = (TextView) itemView.findViewById(R.id.endTime);
        }
    }

    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_booking, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ScheduleAdapter.ViewHolder holder, int position) {

        if(null != schedules){
            holder.mBookieName.setText(schedules.get(position).getmBookieName());
            holder.mBookieDesignation.setText(schedules.get(position).getmBookieDesignation());
            holder.mBookieTeam.setText(schedules.get(position).getmBookieTeam());
            holder.mMeetingType.setText(schedules.get(position).getmMeetingType());
            holder.mMeetingStartTime.setText(schedules.get(position).getmMeetingStartTime());
            holder.mMeetingEndTime.setText(schedules.get(position).getmMeetingEndTime());
        }else if(null != scheduleRealmResults){
            holder.mBookieName.setText(scheduleRealmResults.get(position).getmBookieName());
            holder.mBookieDesignation.setText(scheduleRealmResults.get(position).getmBookieDesignation());
            holder.mBookieTeam.setText(scheduleRealmResults.get(position).getmBookieTeam());
            holder.mMeetingType.setText(scheduleRealmResults.get(position).getmMeetingType());
            holder.mMeetingRoomName.setText(" @ "+scheduleRealmResults.get(position).getmMeetingRoomName());
            holder.mMeetingStartTime.setText(scheduleRealmResults.get(position).getmMeetingStartTime());
            holder.mMeetingEndTime.setText(scheduleRealmResults.get(position).getmMeetingEndTime());
        }else{
            //
        }


    }

    @Override
    public int getItemCount() {
        if(null != schedules)
            return schedules.size();
        else if(null != scheduleRealmResults)
            return scheduleRealmResults.size();
        else
            return 0;

    }
}
