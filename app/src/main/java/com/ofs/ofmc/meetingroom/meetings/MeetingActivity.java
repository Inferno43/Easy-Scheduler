package com.ofs.ofmc.meetingroom.meetings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ofs.ofmc.meetingroom.BaseActivity;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.toolbox.Constants;

/**
 * Created by saravana.subramanian on 12/1/16.
 */

public class MeetingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        Intent args = getIntent();

        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putBundle(Constants.EXTRA,args.getExtras());

       // if(args.getExtras().getString(Constants.FROM).equalsIgnoreCase(Constants.SHARED_CALENDAR))
            replaceFragment(AddMeetingFragment.class,false,fragmentArgs);
        //else
        //    replaceFragment(BookMeetingRoomFragment.class,false,null);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
