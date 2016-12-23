package com.ofs.ofmc.meetingroom.home;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ofs.ofmc.meetingroom.BaseActivity;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.profile.ProfileActivity;
import com.ofs.ofmc.meetingroom.settings.SettingsActivity;
import com.ofs.ofmc.meetingroom.toolbox.NotificationPublisher;

import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends BaseActivity {




    private String dateString;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //notification not working
        NotificationPublisher alarm = new NotificationPublisher();
        alarm.SetAlarmR(this);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch(menuId){
            case R.id.action_settings:
                replaceActivity(SettingsActivity.class,false,null);
                break;
            case R.id.action_profile:
                replaceActivity(ProfileActivity.class,false,null);
                break;

        }
        return super.onOptionsItemSelected(item);
    }




    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch ( position){
                case 0:
                    return new MyCalendarFragment();
                case 1:
                    Fragment shareCalendar = new SharedCalendarFragment();
                    return shareCalendar;

                case 2:
                    return new PublicCalendarFragment();
            }
            return null ;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "My \n Calendar";
                case 1:
                    return "Shared Calendar";
                case 2:
                    return "Public Calendar";
            }
            return null;
        }
    }


    @Override
    public void onBackPressed() {

        new CustomDialog().show(getSupportFragmentManager(),null);
    }


    public static class CustomDialog extends DialogFragment {

        public static CustomDialog newInstance(int title) {
            CustomDialog frag = new CustomDialog();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.drawable.logo)
                    .setTitle("Do you want to close the app?")
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    startActivity(new Intent()
                                            . setAction(Intent.ACTION_MAIN)
                                            .addCategory(Intent.CATEGORY_HOME));
                                }
                            }
                    )
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .create();
        }
    }
}
