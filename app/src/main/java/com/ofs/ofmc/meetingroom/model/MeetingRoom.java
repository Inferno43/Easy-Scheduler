package com.ofs.ofmc.meetingroom.model;

import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saravana.subramanian on 12/8/16.
 */

public class MeetingRoom extends RealmObject {

    @PrimaryKey
    private int id;
    private String mRoomId;
    private String mRoomName;
    private String mRoomPhase;
    private int mSeating;

    public MeetingRoom(){

    }
    public MeetingRoom(int id,String mRoomId, String mRoomName, String mRoomPhase, int mSeating) {
        this.id = id;
        this.mRoomId = mRoomId;
        this.mRoomName = mRoomName;
        this.mRoomPhase = mRoomPhase;
        this.mSeating = mSeating;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmRoomId() {
        return mRoomId;
    }

    public void setmRoomId(String mRoomId) {
        this.mRoomId = mRoomId;
    }

    public String getmRoomName() {
        return mRoomName;
    }

    public void setmRoomName(String mRoomName) {
        this.mRoomName = mRoomName;
    }

    public String getmRoomPhase() {
        return mRoomPhase;
    }

    public void setmRoomPhase(String mRoomPhase) {
        this.mRoomPhase = mRoomPhase;
    }

    public int getmSeating() {
        return mSeating;
    }

    public void setmSeating(int mSeating) {
        this.mSeating = mSeating;
    }


}
