package com.ofs.ofmc.meetingroom.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.ViewController;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.SharedPref;

/**
 * Created by saravana.subramanian on 12/19/16.
 */

public class SettingsImplView implements ViewController {

    private View mRootView;
    private Context context;
    private ClickListener mClickListener;
    private RadioButton listView;
    private RadioButton timeline;
    private RadioGroup notificationInterval;
    private RadioButton _10min;
    private RadioButton _15min;
    private RadioButton _20min;
    private int interval;
    private CheckBox fillProfile;
    private Button save;
    private SharedPref sharedPref;

    private int flow;
    private boolean autoFill;

    /**
     * Controller for Settings Fragment(View -> Controller)
     * @param inflater
     * @param container
     */
    public SettingsImplView(LayoutInflater inflater, ViewGroup container) {

        mRootView = inflater.inflate(R.layout.mvc_view_settings, container, false);
        context = getRootView().getContext();
        sharedPref = new SharedPref();
        listView = (RadioButton)mRootView.findViewById(R.id.showList);
        timeline = (RadioButton)mRootView.findViewById(R.id.showTimeline);
        fillProfile = (CheckBox) mRootView.findViewById(R.id.autofillProfile);
        notificationInterval = (RadioGroup) mRootView.findViewById(R.id.notificationInterval);
        _10min = (RadioButton)mRootView.findViewById(R.id.ten_min);
        _15min = (RadioButton)mRootView.findViewById(R.id.fifteen_min);
        _20min = (RadioButton)mRootView.findViewById(R.id.twenty_min);
        save = (Button) mRootView.findViewById(R.id.saveSettings);
        initView(context);

        listView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mClickListener.setOnClickListener(compoundButton);
            }
        });
        timeline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mClickListener.setOnClickListener(compoundButton);
            }
        });
        fillProfile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mClickListener.setOnClickListener(compoundButton);
            }
        });
        /*notificationInterval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioGroup.getCheckedRadioButtonId();
            }
        });*/
        _10min.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mClickListener.setOnClickListener(compoundButton);
            }
        });
        _15min.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mClickListener.setOnClickListener(compoundButton);
            }
        });
        _20min.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mClickListener.setOnClickListener(compoundButton);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.setOnClickListener(view);
            }
        });

    }

    /**
     * init views inside layout
     * @param context
     */
    void initView(Context context){
        interval = sharedPref.getInteger(context,SharedPref.PREFS_NOTIFICATION_INTERVAL);
        flow = sharedPref.getMap(context).get(SharedPref.PREFS_FLOW);
        autoFill = sharedPref.getBoolean(context,SharedPref.PREFS_AUTOFILL);
        fillProfile.setChecked(autoFill);
        timeline.setChecked(flow == 2);
        listView.setChecked(flow == 1);
        _10min.setChecked(interval == Constants._10MIN);
        _15min.setChecked(interval == Constants._15MIN);
        _20min.setChecked(interval == Constants._20MIN);

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
}
