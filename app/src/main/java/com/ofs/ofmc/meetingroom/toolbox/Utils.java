package com.ofs.ofmc.meetingroom.toolbox;

import com.ofs.ofmc.meetingroom.exceptions.EmptyTextException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by saravana.subramanian on 11/30/16.
 */
public class Utils {
    private static Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
    }

    private Utils() {
    }

    Pattern validUser = Pattern.compile("^[A-Za-z_]\\w{7,29}$");

    public boolean validUserName(String username) throws EmptyTextException{

        return validUser.matcher(username).matches();
    }

    public boolean validpassword(String password) throws EmptyTextException{

        return password.length()>0;
    }

    public static int timeVariant(String timeString,char variant){
        String[] time = timeString.substring(0,4).split ( ":" );
        if(variant == 'H'){
            return Integer.parseInt ( time[0].trim() );
        }else if(variant == 'h'){
            return Integer.parseInt ( time[0].trim() )>12?
                    Integer.parseInt ( time[0].trim() ) - 12:Integer.parseInt ( time[0].trim() );
        }else if(variant =='m')
            return Integer.parseInt ( time[1].trim() );
        else return 0;
    }

    public static int dateVariant(Date date, char variant){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if(variant == 'Y'){
            return year;
        }else if(variant == 'M'){
            return month;
        }else if(variant =='D')
            return day;
        else return 0;
    }

    public static String formatDate(Date dateString){
        SimpleDateFormat spf= new SimpleDateFormat("dd-MMM-yyyy");
        return spf.format(dateString);
    }

}
