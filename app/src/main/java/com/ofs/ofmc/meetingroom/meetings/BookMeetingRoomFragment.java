package com.ofs.ofmc.meetingroom.meetings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ofs.ofmc.meetingroom.BaseFragment;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.model.Schedule;
import com.ofs.ofmc.meetingroom.notifications.AlarmReceiver;
import com.ofs.ofmc.meetingroom.notifications.ParcelableUtils;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.Logr;
import com.ofs.ofmc.meetingroom.toolbox.SharedPref;
import com.ofs.ofmc.meetingroom.toolbox.Utils;

import org.w3c.dom.Comment;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by saravana.subramanian on 12/13/16.
 */

public class BookMeetingRoomFragment extends BaseFragment implements BookMeetingRoomView.ClickListener {

    Calendar mcurrentTime = Calendar.getInstance();
    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
    int minute = mcurrentTime.get(Calendar.MINUTE);
    private SharedPref sharedPref;
    private int notificationInterval;
    private Context context;

    Realm realm;

    BookMeetingRoomView bookMeetingRoomView;

    RealmResults<Schedule> schedules;

    private DatabaseReference mDatabase;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
        context = getContext();
        sharedPref = new SharedPref();
        Bundle fragmentArgs = getArguments();
        bookMeetingRoomView = new BookMeetingRoomImplView(inflater,container,fragmentArgs);
        bookMeetingRoomView.setListener(this);
        getActivity().setTitle(Utils.formatDate(fragmentArgs.getString(Constants.EXTRA_DATE)));
        notificationInterval = sharedPref.getInteger(context,SharedPref.PREFS_NOTIFICATION_INTERVAL);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return bookMeetingRoomView.getRootView();
    }



    @Override
    public void setOnClickListener(View view) {
        int viewId = view.getId();
        switch (viewId){
            case R.id.startTimePicker:
                showPicker(view,viewId);
                break;
            case R.id.endTimePicker:
                showPicker(view,viewId);
                break;
            case R.id.bookRoom:
                //insert db or call webservice
                break;
            case R.id.cancel:
                ((EditText) bookMeetingRoomView.getRootView().findViewById(R.id.bookerName)).setText("");
                break;

        }
    }

    @Override
    public void onSubmitClickListener(final Schedule schedule) {

                if(isFieldsValid(schedule))
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            schedules = realm.where(Schedule.class)
                                    .contains("mMeetingRoomName", schedule.getmMeetingRoomName())
                                    .contains("mDate",schedule.getmDate())
                                    .contains("mMeetingStartTime",schedule.getmMeetingStartTime())

                                    .findAll();
//                            schedules.where().not()
//                                    .between("mMeetingStartTime",Long.parseLong(schedule.getmMeetingStartTime()),
//                                            Long.parseLong(schedules.get(0).getmMeetingEndTime())).or()
//                                    .between("mMeetingEndTime",Long.parseLong(schedule.getmMeetingStartTime()),
//                                            Long.parseLong(schedules.get(0).getmMeetingEndTime())).findAll();
                            if(schedules.size()<1){
                                realm.executeTransactionAsync(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.insertOrUpdate(schedule);
                                        writeNewSchedule(schedule);
                                    }
                                }, new OnSuccess() {
                                    @Override
                                    public void onSuccess() {

                                        scheduleAlarm(schedule);
                                        Snackbar.make(bookMeetingRoomView.getRootView(),"Successfully booked meeting room",Snackbar.LENGTH_LONG).show();
                                    }
                                });

                            }else{
                                Snackbar.make(bookMeetingRoomView.getRootView(),"This Meeting room is already booked for this time",Snackbar.LENGTH_LONG).show();
                            }

                            getFragmentManager().popBackStack();
                        }
                });

    }




    void showPicker(final View view, final int viewId){
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                switch (viewId){
                    case R.id.startTimePicker:
                        ((EditText) bookMeetingRoomView.getRootView().findViewById(R.id.startTime)).setText( selectedHour + ":" + selectedMinute);
                        break;
                    case R.id.endTimePicker:
                        ((EditText) bookMeetingRoomView.getRootView().findViewById(R.id.endTime)).setText( selectedHour + ":" + selectedMinute);
                        break;
                }

            }
        }, hour, minute,false);

        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void scheduleAlarm(Schedule schedule) {
        /**
         * call broadcost reciver
         */
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.setAction("com.alarm.ACTION");
        intent.putExtra(Constants.EXTRA,schedule);

//        byte[] bytes = ParcelableUtils.marshall(schedule);
//        intent.putExtra(Constants.EXTRA_DATA,bytes);
//
//        Bundle bundle = new Bundle();
//        intent.putExtra(Constants.EXTRA_BOOKIE_NAME,schedule.getmBookieName());
//        intent.putExtra(Constants.EXTRA_MEETING_ROOM_NAME,schedule.getmMeetingRoomName());
//        intent.putExtra(Constants.EXTRA_MEETING_TIME,schedule.getmMeetingStartTime());
//        intent.putExtra(Constants.EXTRA_MEETING_DESCRIPTION,schedule.getmMeetingType());
//        intent.putExtra(Constants.EXTRA_BUNDLE,bundle);

        final int _id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), _id, intent,0);
        Calendar calendar = Calendar.getInstance();
        try{
            calendar.set(Calendar.YEAR,Utils.dateVariant(schedule.getmDate(),'Y'));Logr.d(""+Utils.dateVariant(schedule.getmDate(),'Y'));
            calendar.set(Calendar.MONTH,Utils.dateVariant(schedule.getmDate(),'M')-1);Logr.d(""+Utils.dateVariant(schedule.getmDate(),'M'));
            calendar.set(Calendar.DAY_OF_MONTH,Utils.dateVariant(schedule.getmDate(),'D'));Logr.d(""+Utils.dateVariant(schedule.getmDate(),'D'));
            calendar.set(Calendar.HOUR,Utils.timeVariant(schedule.getmMeetingStartTime(),'h'));Logr.d(""+Utils.timeVariant(schedule.getmMeetingStartTime(),'h'));
            calendar.set(Calendar.MINUTE,Utils.timeVariant(schedule.getmMeetingStartTime(),'m'));Logr.d(""+Utils.timeVariant(schedule.getmMeetingStartTime(),'m'));
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
        }catch(Exception e){
            e.printStackTrace();
        }


        Logr.d(""+calendar.getTimeInMillis());
        AlarmManager alarm = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()-Constants._10MIN, pendingIntent);
    }
    boolean isFieldsValid(Schedule schedule){

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR, Utils.timeVariant(schedule.getmMeetingStartTime(),'H'));
        startTime.set(Calendar.MINUTE, Utils.timeVariant(schedule.getmMeetingStartTime(),'m'));

        Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.HOUR, Utils.timeVariant(schedule.getmMeetingEndTime(),'H'));
        endTime.set(Calendar.MINUTE, Utils.timeVariant(schedule.getmMeetingEndTime(),'m'));

        if(schedule.getmBookieName().length()<=0){
            Snackbar.make(bookMeetingRoomView.getRootView(),"Name cannot be empty",Snackbar.LENGTH_LONG).show();
            return false;
        }
        else if(schedule.getmBookieDesignation().length()<=0){
            Snackbar.make(bookMeetingRoomView.getRootView(),"Designation cannot be empty",Snackbar.LENGTH_LONG).show();
            return false;}
        else if(schedule.getmBookieTeam().length()<=0){
            Snackbar.make(bookMeetingRoomView.getRootView(),"Team Cannot be empty",Snackbar.LENGTH_LONG).show();
            return false;}
        else if(schedule.getmDate().length()<=0){
            Snackbar.make(bookMeetingRoomView.getRootView(),"Date cannot be empty",Snackbar.LENGTH_LONG).show();
            return false;}
        else if(schedule.getmMeetingStartTime().length()<=0){
            Snackbar.make(bookMeetingRoomView.getRootView(),"Pick a valid start time",Snackbar.LENGTH_LONG).show();
            return false;}
        else if(schedule.getmMeetingEndTime().length()<=0) {
            Snackbar.make(bookMeetingRoomView.getRootView(),"pick a valid end time",Snackbar.LENGTH_LONG).show();
            return false;}
        else if(schedule.getmMeetingRoom() == null) {
            Snackbar.make(bookMeetingRoomView.getRootView(),"Choose a room from the list",Snackbar.LENGTH_LONG).show();
            return false;}
        else if(startTime.getTimeInMillis()>endTime.getTimeInMillis()){
            Snackbar.make(bookMeetingRoomView.getRootView(),"Invalid Time",Snackbar.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

    private void writeNewSchedule(final Schedule schedule) {

        final String uid = mAuth.getCurrentUser().getUid();


        FirebaseDatabase.getInstance().getReference().child(uid)

                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user information
                        //Schedule newSchedule = dataSnapshot.getValue(Schedule.class);
                        //Schedule newSchedule = new Schedule(schedule.getId(), authorName, commentText);

                        // Push the comment, it will appear in the list
                        mDatabase.child("schedules").push().setValue(schedule);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

//        try{
//            mDatabase.push().child("schedule").child(uid).push().setValue(newSchedule);
//            //mDatabase.child("schedule").child(uid).push().setValue(newSchedule);
//
//            mAuthListener = new FirebaseAuth.AuthStateListener() {
//                @Override
//                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                }
//
//            };
//        }catch (Exception Fe){
//            Fe.printStackTrace();
//        }


    }





}
