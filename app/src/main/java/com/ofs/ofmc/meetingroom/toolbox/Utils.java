package com.ofs.ofmc.meetingroom.toolbox;

import android.net.ParseException;

import com.ofs.ofmc.meetingroom.exceptions.EmptyTextException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

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
//    public static int dateVariant(Date date, char variant){
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH)+1;
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        if(variant == 'Y'){
//            return year;
//        }else if(variant == 'M'){
//            return month;
//        }else if(variant =='D')
//            return day;
//        else return 0;
//    }

    public static int dateVariant(String dateString, char variant){
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

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


    public static Date toDate(String dateString){
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date date = null;
        try {
            date = (Date)formatter.parse(dateString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     *  formats the default date into the under specified format
     * @param dateString
     * @return
     */
    public static String formatDate(String dateString){
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date date = null;
        try {
            date = (Date)formatter.parse(dateString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" +         cal.get(Calendar.YEAR);
        System.out.println("formatedDate : " + formatedDate);
        return formatedDate;
//        try{
//            SimpleDateFormat spf= new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
//            return spf.format(dateString);
//        }catch (ParseException e){
//            return "Book your meeting room";
//        }


    }

}
