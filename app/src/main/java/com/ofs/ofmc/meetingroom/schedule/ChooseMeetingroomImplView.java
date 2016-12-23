package com.ofs.ofmc.meetingroom.schedule;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.model.MeetingRoom;
import com.ofs.ofmc.meetingroom.schedule.adapter.MeetinRoomsAdapter;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by saravana.subramanian on 12/8/16.
 */

public class ChooseMeetingroomImplView implements ChooseMeetingroomView {
    private View mRootView;
    private RecyclerView meeting_rooms_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private MeetinRoomsAdapter mAdapter;
    private ClickListener mClickListener;

    Realm realm;
    private RealmResults<MeetingRoom> meetingRooms;

    public ChooseMeetingroomImplView(LayoutInflater inflater, ViewGroup container,Bundle args) {
        mRootView = inflater.inflate(R.layout.mvc_view_list, container, false);
        meeting_rooms_view = (RecyclerView) mRootView.findViewById(R.id.listView);

        mLayoutManager = new LinearLayoutManager(getRootView().getContext());
        meeting_rooms_view.setLayoutManager(mLayoutManager);
        realm = Realm.getDefaultInstance();
        meetingRooms = realm.where(MeetingRoom.class).distinct("id");//loads all meeting rooms

        mAdapter = new MeetinRoomsAdapter(meetingRooms, new ClickListener() {
            @Override
            public void setOnClickListener(View view) {

            }
            @Override
            public void setOnListItemChooseListener(MeetingRoom meetingRoom, int position) {
                if(mClickListener!=null)
                    mClickListener.setOnListItemChooseListener(meetingRoom,position);
            }
        });
        meeting_rooms_view.setAdapter(mAdapter);

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
