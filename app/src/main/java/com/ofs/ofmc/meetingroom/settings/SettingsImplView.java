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

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.ViewController;
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
        flow = sharedPref.getMap(context).get(SharedPref.PREFS_FLOW);
        autoFill = sharedPref.getBoolean(context,SharedPref.PREFS_AUTOFILL);
        fillProfile.setChecked(autoFill);
        timeline.setChecked(flow==2);
        listView.setChecked(flow==1);

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
