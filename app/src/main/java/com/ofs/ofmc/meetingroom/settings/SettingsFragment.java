package com.ofs.ofmc.meetingroom.settings;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.ofs.ofmc.meetingroom.BaseFragment;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.ViewController;
import com.ofs.ofmc.meetingroom.toolbox.SharedPref;

import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsFragment extends BaseFragment implements ViewController.ClickListener{

    private SharedPref sharedPref;
    private Context context;
    ViewController settingsImplView;
    private HashMap<String,Integer> map;
    private boolean autofill;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        settingsImplView = new SettingsImplView(inflater,container);
        settingsImplView.setListener(this);
        sharedPref = new SharedPref();
        context = getActivity();
        map = new HashMap<>();
        return settingsImplView.getRootView();
    }

    @Override
    public void setOnClickListener(View view) {
        int viewId = view.getId();
        switch(viewId){

            case R.id.showList:
                if(((RadioButton)view).isChecked()){
                    map.put(SharedPref.PREFS_FLOW,SharedPref.PREFS_LIST);
                }
                break;
            case R.id.showTimeline:
                if(((RadioButton)view).isChecked()){
                    map.put(SharedPref.PREFS_FLOW,SharedPref.PREFS_TIMELINE);
                }
                break;
            case R.id.autofillProfile:
                autofill = ((CheckBox)view).isChecked();
                break;
            case R.id.saveSettings:
                sharedPref.save(context,map);
                sharedPref.save(context,autofill);
                Snackbar.make(settingsImplView.getRootView(),"Saved your preference successfully",Snackbar.LENGTH_LONG).show();
                Handler handler = new Handler();
                handler.postDelayed (new Runnable() {
                    @Override
                    public void run() {
                        getActivity().finish();
                    }
                },1000);
                break;

        }
    }
}
