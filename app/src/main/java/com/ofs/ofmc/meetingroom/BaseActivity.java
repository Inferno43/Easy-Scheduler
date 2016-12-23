package com.ofs.ofmc.meetingroom;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ofs.ofmc.meetingroom.callbacks.AbstractActivityCallback;
import com.ofs.ofmc.meetingroom.callbacks.AbstractFragmentCallback;
import com.ofs.ofmc.meetingroom.toolbox.Config;
import com.ofs.ofmc.meetingroom.toolbox.NotificationUtils;

public class BaseActivity extends AppCompatActivity implements AbstractFragmentCallback,AbstractActivityCallback{




    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };


        // Show the default fragment if the application is not restored
        /*if (savedInstanceState == null) {
            replaceFragment(SplashScreen.class, false, null);
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    @Override
    public void replaceFragment(Class<? extends Fragment> claz, boolean addToBackStack, Bundle args) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment newFragment;
        String backStateName ;
        try {
            // Create new fragment
            newFragment = claz.newInstance();
            backStateName = newFragment.getClass().getName();
            if (args != null) newFragment.setArguments(args);
        } catch (InstantiationException e) {
            e.printStackTrace();
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        }

        if (addToBackStack) {
            // Add this transaction to the back stack
            ft.addToBackStack(backStateName);
        }

        // Change to a new fragment
        ft.replace(R.id.container, newFragment, claz.getClass().getSimpleName());
        ft.commit();
    }

    @Override
    public void replaceActivity(Class<? extends Activity> claz, boolean addToBackStack, Bundle args) {

        Intent intent = new Intent(this,claz);
        if(null!=args) intent.putExtras(args);
        intent.addFlags(addToBackStack?Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP:Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

}
