package com.ofs.ofmc.meetingroom.login;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ofs.ofmc.meetingroom.BaseActivity;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.SplashScreen;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        replaceFragment(SplashScreen.class,false,null);
    }
}
