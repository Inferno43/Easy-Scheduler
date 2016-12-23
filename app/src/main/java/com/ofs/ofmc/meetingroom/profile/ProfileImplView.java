package com.ofs.ofmc.meetingroom.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.model.Profile;
import com.ofs.ofmc.meetingroom.toolbox.Constants;

import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by saravana.subramanian on 12/20/16.
 */

public class ProfileImplView implements ProfileView {

    private View mRootView;
    private Context context;
    private ClickListener mClickListener;
    private EditText name;
    private EditText employeeId;
    private EditText employeeDesignation;
    private EditText employeeTeam;
    private EditText employeePhase;
    private Button save;

    Realm realm;

    private HashMap<String,String> profilemap;
    private Profile profile;

    public ProfileImplView(LayoutInflater inflater, ViewGroup container){
        mRootView = inflater.inflate(R.layout.mvc_view_profile,container,false);
        profilemap = new HashMap<>();
        realm = Realm.getDefaultInstance();
        profile = realm.isEmpty()?null:realm.where(Profile.class).findFirst();
        name = (EditText)mRootView.findViewById(R.id.name);
        employeeId =  (EditText)mRootView.findViewById(R.id.employeeid);
        employeeDesignation =  (EditText)mRootView.findViewById(R.id.designation);
        employeeTeam =  (EditText)mRootView.findViewById(R.id.employeeTeam);
        employeePhase =  (EditText)mRootView.findViewById(R.id.phase);
        save = (Button)mRootView.findViewById(R.id.save);
        initView(profile);
        //profile = new Profile();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilemap.put(Constants.EMPLOYEE_NAME,name.getText().toString());
                profilemap.put(Constants.EMPLOYEE_ID,employeeId.getText().toString());
                profilemap.put(Constants.EMPLOYEE_DESIGNATION,employeeDesignation.getText().toString());
                profilemap.put(Constants.EMPLOYEE_TEAM,employeeTeam.getText().toString());
                profilemap.put(Constants.EMPLOYEE_PHASE,employeePhase.getText().toString());

                mClickListener.onSaveClickListener(save,profilemap);
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


    void initView(Profile profile){

        try{
            if(profile!=null){
                name.setText(profile.getmEmployeename());
                employeeId.setText(profile.getmEmployeeId());
                employeeDesignation.setText(profile.getmEmployeeDesignation());
                employeeTeam.setText(profile.getmEmployeeTeam());
                employeePhase.setText(profile.getmEmployeePhase());
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
