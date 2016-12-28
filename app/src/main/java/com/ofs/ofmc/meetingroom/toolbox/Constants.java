package com.ofs.ofmc.meetingroom.toolbox;

/**
 * Created by saravana.subramanian on 11/30/16.
 *
 * Contains constants used across the app
 */
public class Constants {
    private static Constants ourInstance = new Constants();

    public static Constants getInstance() {
        return ourInstance;
    }

    private Constants() {
    }

    public enum HOME {

        MY_CALENDAR,
        SHARED_CALENDAR,
        PUBLIC_CALENDAR

    }

    public static String FROM  = "from";
    public static String WHERE = "where";
    public static String LOGIN = "login";
    public static String HOME  = "home";
    public static String MY_CALENDAR  = "home.MyCalendar";
    public static String SHARED_CALENDAR  = "home.SharedCalendar";
    public static String PUBLIC_CALENDAR  = "home.PublicCalendar";
    public static String ADDMEETING = "add";



    public static String EXTRA = "string.Extra";
    public static String EXTRA_DATE = "string.Extra.Date";
    public static String EXTRA_MEETING_ROOM_NAME = "string.Extra.MeetingRoom.Name";


    //Profile Keys
    public static String EMPLOYEE_NAME = "employeeName";
    public static String EMPLOYEE_ID = "employeeId";
    public static String EMPLOYEE_DESIGNATION = "employeeDesignation";
    public static String EMPLOYEE_TEAM = "employeeTeam";
    public static String EMPLOYEE_PHASE = "employeePhase";


    //Interval
    public static int _5MIN  = 300000;
    public static int _10MIN = 600000;
    public static int _15MIN = 900000;
    public static int _20MIN = 1200000;
    public static int _10SEC = 10000;
    public static int _30SEC = 30000;
}
