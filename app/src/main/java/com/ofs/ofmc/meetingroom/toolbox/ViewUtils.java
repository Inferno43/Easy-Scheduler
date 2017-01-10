package com.ofs.ofmc.meetingroom.toolbox;

import android.app.Activity;
import android.os.Bundle;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by saravana.subramanian on 11/30/16.
 * Contains Utility functions used across the app
 */
public class ViewUtils {
    private MyProgressDialog myProgressDialog;

    private static ViewUtils ourInstance = new ViewUtils();

    public static ViewUtils getInstance() {
        return ourInstance;
    }

    private ViewUtils() {
    }

    public enum DateFormat{

        FORMAT1,
        FORMAT2,
        FORMAT3

    }

    public String getTodayTime(boolean format,DateFormat dateFormat){

        String timeStamp =  String.valueOf(new Timestamp(System.currentTimeMillis()));
        if(format){

            switch (dateFormat){
                case FORMAT1:
                    SimpleDateFormat Format1 = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss:S");
                    return Format1.format(timeStamp);
                case FORMAT2:
                    SimpleDateFormat Format2 = new SimpleDateFormat("dd/MM/yyyy");
                    return Format2.format(timeStamp);
                case FORMAT3:
                    break;
                default:
                    break;

            }
        }

        return timeStamp;

    }

    /**
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public String getFormattedTime(Date date, DateFormat dateFormat){

        if(date!=null){

            switch (dateFormat){
                case FORMAT1:
                    SimpleDateFormat Format1 = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss:S");
                    return Format1.format(date);
                case FORMAT2:
                    SimpleDateFormat Format2 = new SimpleDateFormat("dd/MM/yyyy");
                    return Format2.format(date);
                case FORMAT3:
                    SimpleDateFormat Format3 = new SimpleDateFormat("MMM-dd-yyyy");
                    return Format3.format(date);

                default:
                    break;

            }
        }

        return String.valueOf(date);

    }



    public void initProgress(Activity activity, String title){

        Bundle args = new Bundle();
        args.putString("title", title);
        myProgressDialog = new MyProgressDialog();
        myProgressDialog.setArguments(args);
        myProgressDialog.show(activity.getFragmentManager(),"dialog");
    }
    public void dismissProgress(){
        if(myProgressDialog!=null && myProgressDialog.isInLayout())
            myProgressDialog.dismiss();
    }

}
