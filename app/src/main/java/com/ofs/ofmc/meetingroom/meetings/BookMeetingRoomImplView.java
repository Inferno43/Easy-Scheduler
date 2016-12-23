package com.ofs.ofmc.meetingroom.meetings;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.model.MeetingRoom;
import com.ofs.ofmc.meetingroom.model.Profile;
import com.ofs.ofmc.meetingroom.model.Schedule;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.Logr;
import com.ofs.ofmc.meetingroom.toolbox.SharedPref;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by saravana.subramanian on 12/13/16.
 */

public class BookMeetingRoomImplView implements BookMeetingRoomView {


    private View mRootView;
    private ClickListener mClickListener;
    private ImageView startTimePicker;
    private ImageView endTimePicker;
    private ImageView meetingRoomPicker;

    private EditText mBookieName;
    private EditText mBookieDesignation;
    private EditText mBookieTeam;
    private EditText mMeetingType;
    private EditText mMeetingRoom;
    private CheckBox mIsMeetingTentative;
    private EditText mStartTime;
    private EditText mEndTime;
    private Button   mBookRoom;
    private Button   mCancel;

    private Calendar calendar;
    private Schedule schedule;
    private MeetingRoom meetingRoom;
    private RealmResults<MeetingRoom> meetingRooms;
    private Profile loadProfile;
    private Realm realm;
    private int lastRowId;

    private PopupWindow popUpWindow;

    PopupMenu popup ;


    private  SharedPref sharedPref;
    private Context context;
    private Bundle fragmentArgs;



    BookMeetingRoomImplView(LayoutInflater inflater, ViewGroup container, Bundle args){
        mRootView = inflater.inflate(R.layout.mvc_view_choose_timing, container, false);
        realm = Realm.getDefaultInstance();
        context = getRootView().getContext();
        fragmentArgs = args;
        mBookieName = (EditText) mRootView.findViewById(R.id.bookerName);
        mBookieDesignation = (EditText) mRootView.findViewById(R.id.bookerDesignation);
        mBookieTeam = (EditText) mRootView.findViewById(R.id.bookerTeam);
        mMeetingType = (EditText) mRootView.findViewById(R.id.meetingType);
        mMeetingRoom = (EditText) mRootView.findViewById(R.id.roomName);
        mIsMeetingTentative = (CheckBox)mRootView.findViewById(R.id.bookAsTentative);
        mStartTime = (EditText) mRootView.findViewById(R.id.startTime);
        mEndTime = (EditText) mRootView.findViewById(R.id.endTime);
        startTimePicker = (ImageView) mRootView.findViewById(R.id.startTimePicker);
        endTimePicker = (ImageView) mRootView.findViewById(R.id.endTimePicker);
        meetingRoomPicker = (ImageView)mRootView.findViewById(R.id.chooseMeetingRoom);
        mBookRoom = (Button)mRootView.findViewById(R.id.bookRoom);
        mCancel = (Button)mRootView.findViewById(R.id.cancel);
        calendar = Calendar.getInstance();
        sharedPref = new SharedPref();
        popup = new PopupMenu(context, meetingRoomPicker);
        initPopup();
        autofillProfile(sharedPref);
        try{
            if(!realm.isEmpty()){
                lastRowId = !realm.isEmpty()?realm.where(Schedule.class).max("id").intValue():0;
            }

        }catch (Exception e){
            lastRowId = 0;
        }

        startTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener!=null)
                    mClickListener.setOnClickListener(startTimePicker);
            }
        });
        endTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener!=null)
                    mClickListener.setOnClickListener(endTimePicker);
            }
        });
        mIsMeetingTentative.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mIsMeetingTentative.setChecked(isChecked);
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener!=null)
                    mClickListener.setOnClickListener(mCancel);
            }
        });
        meetingRoomPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        for(MeetingRoom mr:meetingRooms){
                            if(mr.getmRoomName().equals(item.getTitle())){
                                meetingRoom = mr;
                            }
                        }
                        mMeetingRoom.setText(meetingRoom.getmRoomName());
                        return true;
                    }
                });

                popup.show();//showing popup menu

                /*popUpWindow = new PopupWindow(mRootView.getContext());
                ListView listView = new ListView(mRootView.getContext());

                meetingRooms = realm.where(MeetingRoom.class).distinct("id");
                String[] resultArray = new String[meetingRooms.size()];
                for (int i= 0;i<meetingRooms.size();i++){
                    resultArray[i] = meetingRooms.get(i).getmRoomName();
                }
                listView.setAdapter(mAdapter(resultArray));
                listView.setOnItemClickListener(new DropdownOnItemClickListener());
                popUpWindow.setContentView(listView);
                //popUpWindow.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                popUpWindow.setClippingEnabled(true);
                popUpWindow.showAsDropDown(mMeetingRoom);*/
            }
        });

        mBookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schedule = new Schedule(lastRowId+1,mBookieName.getText().toString(),mBookieDesignation.getText().toString(),
                        mBookieTeam.getText().toString(),mMeetingType.getText().toString(),mIsMeetingTentative.isChecked(),
                        fragmentArgs.getString(Constants.EXTRA_DATE),mStartTime.getText().toString(),mEndTime.getText().toString(),
                        mMeetingRoom.getText().toString(),meetingRoom);
                Logr.d(""+mMeetingRoom.getText().toString());
                if(mClickListener!=null)
                    mClickListener.onSubmitClickListener(schedule);
                else
                    Snackbar.make(mRootView,"fields not valid",Snackbar.LENGTH_SHORT).show();
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



    void autofillProfile(SharedPref sharedPref){
        boolean autofill = sharedPref.getBoolean(context,SharedPref.PREFS_AUTOFILL);
        loadProfile = realm.where(Profile.class).findFirst();
        if(autofill && loadProfile!=null){
            mBookieName.setText(loadProfile.getmEmployeename());
            mBookieDesignation.setText(loadProfile.getmEmployeeDesignation());
            mBookieTeam.setText(loadProfile.getmEmployeeTeam());
        }else{
            Toast.makeText(getRootView().getContext(),"Your Profile is not completed",Toast.LENGTH_LONG).show();
        }

    }

    private void initPopup(){
        meetingRooms = realm.where(MeetingRoom.class).distinct("id");
        for (int i= 0;i<meetingRooms.size();i++){
            popup.getMenu().add(meetingRooms.get(i).getmRoomName());
        }
    }

    private ArrayAdapter<String> mAdapter(String array[]) {

        return new ArrayAdapter<String>(getRootView().getContext(),
                android.R.layout.simple_list_item_1, array) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // setting the ID and text for every items in the list

                String text = getItem(position);

                // visual settings for the list item
                TextView listItem = new TextView(getContext());

                listItem.setText(text);
                listItem.setTag(position);
                listItem.setTextSize(22);
                listItem.setPadding(10, 10, 10, 10);

                return listItem;
            }
        };
    }

    public class DropdownOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int postion, long arg3) {

            // get the context and main activity to access variables
            Context mContext = v.getContext();

            // add some animation when a list item was clicked
            Animation fadeInAnimation = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in);
            fadeInAnimation.setDuration(10);
            v.startAnimation(fadeInAnimation);
            meetingRoom = meetingRooms.get(postion);
            mMeetingRoom.setText(meetingRoom.getmRoomName());
            // dismiss the pop up
            popUpWindow.dismiss();

            // get the text and set it as the button text

            Toast.makeText(mContext, "Selected  " + meetingRooms.get(postion).getmRoomName(), Toast.LENGTH_SHORT).show();


        }

    }

}
