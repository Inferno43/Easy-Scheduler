package com.ofs.ofmc.meetingroom.schedule;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ofs.ofmc.meetingroom.BaseActivity;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.SharedPref;

import java.util.ArrayList;
import java.util.Date;

public class ScheduleActivity extends BaseActivity{

    SharedPref sharedPref;
    private int FLOW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPref = new SharedPref();
        FLOW = sharedPref.getInteger(this,SharedPref.PREFS_FLOW);
        //Intent args = getIntent().getExtras();
        Bundle fragmentArgs = getIntent().getExtras();
        //fragmentArgs.putBundle(Constants.EXTRA,args.getExtras());

        if(fragmentArgs.getString(Constants.FROM).equalsIgnoreCase(Constants.SHARED_CALENDAR))
            replaceFragment(ChooseMeetingroomFragment.class,false,fragmentArgs);
        else{
            if(FLOW == 1){
                replaceFragment(ScheduleListFragment.class,false, fragmentArgs);
            }else if(FLOW == 2){
                replaceFragment(TodayTimelineFragment.class,false, fragmentArgs);
            }
        }


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
