package com.ofs.ofmc.meetingroom.toolbox;

import com.ofs.ofmc.meetingroom.exceptions.EmptyTextException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by saravana.subramanian on 11/30/16.
 *
 * Contains utility fuctions used across the app
 */
public class Utils {
    private static Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
    }

    private Utils() {
    }

    Pattern validUser = Pattern.compile("^[A-Za-z_]\\w{7,29}$");

    /**
     *
     * Validates the string with the above and throws exception if the string is empty
     *
     * @param username
     * @return boolean
     * @throws EmptyTextException
     */
    public boolean validUserName(String username) throws EmptyTextException{

        return validUser.matcher(username).matches();
    }

    /**
     * Validates the password string and throws exception if the string is empty
     * @param password
     * @return boolean
     * @throws EmptyTextException
     */
    public boolean validpassword(String password) throws EmptyTextException{

        return password.length()>0;
    }

    /**
     * split the string and returns time hours or minutes based on variant param
     * @param timeString
     * @param variant
     * @return time variant
     */
    public static int timeVariant(String timeString,char variant){
        try{

            String[] time = timeString.length()==5?timeString.substring(0,5).split ( ":" ):timeString.substring(0,4).split ( ":" );
            if(variant == 'H'){
                return Integer.parseInt ( time[0].trim() );
            }else if(variant == 'h'){
                return Integer.parseInt ( time[0].trim() )>12?
                        Integer.parseInt ( time[0].trim() ) - 12:Integer.parseInt ( time[0].trim() );
            }else if(variant =='m')
                return Integer.parseInt ( time[1].trim() );
            else return 0;
        }catch(StringIndexOutOfBoundsException e){

        }
        return 0;
    }

    /**
     * process the date string and returns date year or month or day based on variant param
     *
     * @param date
     * @param variant
     * @return date variant
     */
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

    /**
     *  formats the default date into the under specified format
     * @param dateString
     * @return
     */
    public static String formatDate(Date dateString){
        SimpleDateFormat spf= new SimpleDateFormat("dd-MMM-yyyy");
        return spf.format(dateString);
    }

}
