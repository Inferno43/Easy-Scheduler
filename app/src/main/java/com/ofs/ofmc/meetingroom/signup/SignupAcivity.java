package com.ofs.ofmc.meetingroom.signup;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ofs.ofmc.meetingroom.BaseActivity;
import com.ofs.ofmc.meetingroom.R;

/**
 * Created by saravana.subramanian on 1/4/17.
 */

public class SignupAcivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        replaceFragment(SignupFragment.class,false,null);
    }
}
