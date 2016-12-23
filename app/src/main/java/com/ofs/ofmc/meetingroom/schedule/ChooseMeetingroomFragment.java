package com.ofs.ofmc.meetingroom.schedule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ofs.ofmc.meetingroom.BaseActivity;
import com.ofs.ofmc.meetingroom.BaseFragment;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.home.SharedCalendarFragment;
import com.ofs.ofmc.meetingroom.model.MeetingRoom;
import com.ofs.ofmc.meetingroom.profile.ProfileActivity;
import com.ofs.ofmc.meetingroom.settings.SettingsActivity;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.SharedPref;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saravana.subramanian on 12/8/16.
 */

public class ChooseMeetingroomFragment extends BaseFragment implements ChooseMeetingroomView.ClickListener {

    ChooseMeetingroomView chooseMeetingroomView;
    Bundle fragmentArgs;
    private String dateString;
    SharedPref sharedPref;
    private int FLOW;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try{
            fragmentArgs = getArguments();
        }catch (Exception e){
            fragmentArgs = null;
        }
        getActivity().setTitle("Choose Meeting Room");
        setHasOptionsMenu(false);
        sharedPref = new SharedPref();
        FLOW = sharedPref.getInteger(getContext(),SharedPref.PREFS_FLOW);

        chooseMeetingroomView = new ChooseMeetingroomImplView(inflater, container,fragmentArgs);
        chooseMeetingroomView.setListener(this);


        return chooseMeetingroomView.getRootView();
    }

    @Override
    public void setOnClickListener(View view) {

    }

    @Override
    public void setOnListItemChooseListener(MeetingRoom meetingRoom, int position) {
        Snackbar.make(chooseMeetingroomView.getRootView(), meetingRoom.getmRoomName() +"is selected",
                Snackbar.LENGTH_SHORT).show();

        if(FLOW == 1){
            fragmentArgs.putString(Constants.EXTRA_MEETING_ROOM_NAME,meetingRoom.getmRoomName());
            replaceFragment(ScheduleListFragment.class,true, fragmentArgs);
        }else if(FLOW == 2){
            fragmentArgs.putString(Constants.EXTRA_MEETING_ROOM_NAME,meetingRoom.getmRoomName());
            replaceFragment(TodayTimelineFragment.class,true,fragmentArgs);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_meeting_room, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch(menuId){
            case R.id.action_sort_location:
                Toast.makeText(getContext(),"Not Implemented",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_sort_seating:
                Toast.makeText(getContext(),"Not Implemented",Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }




}
