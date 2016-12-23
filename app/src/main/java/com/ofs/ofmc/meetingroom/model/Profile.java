package com.ofs.ofmc.meetingroom.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saravana.subramanian on 12/20/16.
 */

public class Profile extends RealmObject{

    @PrimaryKey
    private int id;
    private String mEmployeename;
    private String mEmployeeId;
    private String mEmployeeDesignation;
    private String mEmployeeTeam;
    private String mEmployeePhase;

    public Profile(int id, String mEmployeename, String mEmployeeId, String mEmployeeDesignation, String mEmployeeTeam, String mEmployeePhase) {
        this.id = id;
        this.mEmployeename = mEmployeename;
        this.mEmployeeId = mEmployeeId;
        this.mEmployeeDesignation = mEmployeeDesignation;
        this.mEmployeeTeam = mEmployeeTeam;
        this.mEmployeePhase = mEmployeePhase;
    }

    public Profile() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmEmployeename() {
        return mEmployeename;
    }

    public void setmEmployeename(String mEmployeename) {
        this.mEmployeename = mEmployeename;
    }

    public String getmEmployeeId() {
        return mEmployeeId;
    }

    public void setmEmployeeId(String mEmployeeId) {
        this.mEmployeeId = mEmployeeId;
    }

    public String getmEmployeeDesignation() {
        return mEmployeeDesignation;
    }

    public void setmEmployeeDesignation(String mEmployeeDesignation) {
        this.mEmployeeDesignation = mEmployeeDesignation;
    }

    public String getmEmployeeTeam() {
        return mEmployeeTeam;
    }

    public void setmEmployeeTeam(String mEmployeeTeam) {
        this.mEmployeeTeam = mEmployeeTeam;
    }

    public String getmEmployeePhase() {
        return mEmployeePhase;
    }

    public void setmEmployeePhase(String mEmployeePhase) {
        this.mEmployeePhase = mEmployeePhase;
    }
}
