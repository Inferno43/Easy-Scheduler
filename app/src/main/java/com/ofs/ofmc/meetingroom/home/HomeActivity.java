package com.ofs.ofmc.meetingroom.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
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

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.ofs.ofmc.meetingroom.BaseActivity;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.model.Schedule;
import com.ofs.ofmc.meetingroom.notifications.NotificationHandler;
import com.ofs.ofmc.meetingroom.profile.ProfileActivity;
import com.ofs.ofmc.meetingroom.settings.SettingsActivity;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.Logr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.IOException;

/**
 * Landing screen after login shows 3 calendar
 */

public class HomeActivity extends BaseActivity {



    NotificationHandler nHandler;
    private String dateString;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Context context;
    Toolbar toolbar;
    Realm realm;
    RealmResults<Schedule> schedules;


    Handler mHandler;

    List<TapTarget> tapTargetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nHandler = NotificationHandler.getInstance(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        realm = Realm.getDefaultInstance();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("schedules");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                insertIntoRealm(dataSnapshot.getValue(Schedule.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                insertIntoRealm(dataSnapshot.getValue(Schedule.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //test notification
//        mHandler = new Handler();
//        Runnable mRunnable = new Runnable() {
//            @Override
//            public void run() {
//                nHandler.createSimpleNotification(context);
//            }
//        };mHandler.postDelayed(mRunnable,5000);


        //tut().start();

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
            case R.id.action_logout:
                new CustomDialog().show(getSupportFragmentManager(),null);
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
                    return "Shared \n Calendar";
                case 2:
                    return "Public \n Calendar";
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

    TapTargetSequence tut(){
        final TapTargetSequence targetSequence = new TapTargetSequence(this)
                .targets(TapTarget.forToolbarOverflow(toolbar,"Settings","Click here")
                        .drawShadow(true)
                        .tintTarget(true)
                        .transparentTarget(true)
                        .id(1))
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        startActivityForResult(new Intent(context,SettingsActivity.class)
                                .putExtra(Constants.REQUEST,Constants.TUT_SETTINGS), Constants.TUT_SETTINGS);
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                });
        return targetSequence;
    }

    void insertIntoRealm(final Schedule schedule){
        Logr.d(schedule.getmBookieName()+""+schedule.getmMeetingStartTime()+""+schedule.getmMeetingType()+""+schedule.getmMeetingRoomName());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                schedules = realm.where(Schedule.class)
                        .contains("mMeetingRoomName", schedule.getmMeetingRoomName())
                        .contains("mDate",schedule.getmDate())
                        .contains("mMeetingStartTime",schedule.getmMeetingStartTime())

                        .findAll();
                if(schedules.size()<1){

                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.insertOrUpdate(schedule);
                        }
                    }, new OnSuccess() {
                        @Override
                        public void onSuccess() {

                            //scheduleAlarm(schedule);
                            Snackbar.make(mViewPager,"Successfully synced with cloud",Snackbar.LENGTH_LONG).show();
                        }
                    });

                }else{
                    Snackbar.make(mViewPager,"Successfully synced with cloud",Snackbar.LENGTH_LONG).show();
                }

                exportDatabase();
            }
        });
    }



    public void exportDatabase() {

        // init realm
        Realm realm = Realm.getDefaultInstance();

        File exportRealmFile = null;
        try {
            // get or create an "export.realm" file
            exportRealmFile = new File(getExternalCacheDir(), "export.realm");

            // if "export.realm" already exists, delete
            exportRealmFile.delete();

            // copy current realm to "export.realm"
            realm.writeCopyTo(exportRealmFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        realm.close();
        // init email intent and add export.realm as attachment
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "saravana.subramanian@object-frontier.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "YOUR SUBJECT");
        intent.putExtra(Intent.EXTRA_TEXT, "YOUR TEXT");
        Uri u = Uri.fromFile(exportRealmFile);
        intent.putExtra(Intent.EXTRA_STREAM, u);

        // start email intent
        startActivity(Intent.createChooser(intent, "YOUR CHOOSER TITLE"));
    }


}
