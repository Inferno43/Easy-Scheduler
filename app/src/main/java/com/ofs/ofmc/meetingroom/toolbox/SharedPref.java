package com.ofs.ofmc.meetingroom.toolbox;

import android.app.IntentService;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by saravana.subramanian on 12/19/16.
 *
 * Shared preference class to store app information and settings
 */

public class SharedPref {


    private static SharedPref ourInstance = new SharedPref();

    public static SharedPref getInstance() {
        return ourInstance;
    }

    public static final String PREFS_MEETING = "MeetingRoom";
    public static final String PREFS_USERNAME = "userName";
    public static final String PREFS_PASSWORD = "password";
    public static final String PREFS_FLOW = "flow";
    public static final String PREFS_AUTOFILL = "autofill";
    public static final String PREFS_NOTIFICATION_INTERVAL = "notificationInterval";


    public static final String PREFS_FRESH_INSTALL = "freshInstall";


    public static final int PREFS_LIST = 1;
    public static final int PREFS_TIMELINE = 2;

    public SharedPref() {
        super();
    }


    public void init(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_MEETING, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putBoolean(PREFS_FRESH_INSTALL,true);
        editor.putInt(PREFS_FLOW,PREFS_LIST);
        editor.putInt(PREFS_NOTIFICATION_INTERVAL,Constants._10MIN);
        editor.putBoolean(PREFS_AUTOFILL,false);
        editor.apply(); //4
    }

    public void save(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_MEETING, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        //editor.putString(PREFS_KEY, text); //3

        editor.commit(); //4
    }

    public void saveProfile(Context context, HashMap<String,Integer> map) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_MEETING, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2


        if(map.containsKey(PREFS_FLOW))
            editor.putInt(PREFS_FLOW, map.get(PREFS_FLOW));
        if(map.containsKey(PREFS_NOTIFICATION_INTERVAL))
            editor.putInt(PREFS_NOTIFICATION_INTERVAL, map.get(PREFS_NOTIFICATION_INTERVAL));
        //editor.putString(PREFS_KEY, String.valueOf(map.get("123"))); //3

        editor.commit(); //4
    }


    public void save(Context context, HashMap<String,Integer> map) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_MEETING, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2


        if(map.containsKey(PREFS_FLOW))
            editor.putInt(PREFS_FLOW, map.get(PREFS_FLOW));
        //editor.putString(PREFS_KEY, String.valueOf(map.get("123"))); //3

        editor.commit(); //4
    }

    public void save(Context context, String Key, boolean boole) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_MEETING, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //

        editor.putBoolean(Key, boole);

        //editor.putString(PREFS_KEY, String.valueOf(map.get("123"))); //3

        editor.commit(); //4
    }


    public String getString(Context context,String PREFS_KEY) {
        SharedPreferences settings;
        String text = null;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_MEETING, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY, null);
        return text;
    }

    public Boolean getBoolean(Context context,String PREFS_KEY) {
        SharedPreferences settings;
        boolean value;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_MEETING, Context.MODE_PRIVATE);
        value = settings.getBoolean(PREFS_KEY, false);
        return value;
    }

    public Integer getInteger(Context context,String PREFS_KEY) {
        SharedPreferences settings;
        int value;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_MEETING, Context.MODE_PRIVATE);
        value = settings.getInt(PREFS_KEY, 0);
        return value;
    }


    public HashMap<String,Integer> getMap(Context context) {
        SharedPreferences settings;
        HashMap<String,Integer>   map = new HashMap<>();
        settings = context.getSharedPreferences(PREFS_MEETING, Context.MODE_PRIVATE);
        map.put(PREFS_FLOW,settings.getInt(PREFS_FLOW,1));
        return map;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_MEETING, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_MEETING, Context.MODE_PRIVATE);
        editor = settings.edit();

        //editor.remove(PREFS_KEY);
        editor.commit();
    }
}
