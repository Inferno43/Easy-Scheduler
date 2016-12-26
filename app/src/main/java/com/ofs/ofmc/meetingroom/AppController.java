package com.ofs.ofmc.meetingroom;

import android.app.Application;
import android.widget.Toast;

import com.ofs.ofmc.meetingroom.model.MeetingRoom;
import com.ofs.ofmc.meetingroom.model.Schedule;
import com.ofs.ofmc.meetingroom.toolbox.SharedPref;

import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by saravana.subramanian on 11/29/16.
 *
 * Singleton Application class for initializing DB and shared-preference
 */
public class AppController extends Application {

    private Realm realm;
    private SharedPref sharedPref;
    private static AppController ourInstance = new AppController();

    public static AppController getInstance() {
        return ourInstance;
    }

    public AppController() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);
        try {
            realm = Realm.getDefaultInstance();
        } catch (RealmMigrationNeededException r) {
            RealmConfiguration config = new RealmConfiguration
                    .Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.deleteRealm(config);
            realm = Realm.getDefaultInstance();
        }
        sharedPref = new SharedPref();
        if(!sharedPref.getBoolean(this,SharedPref.PREFS_FRESH_INSTALL))
            sharedPref.init(this);//init shared pref with default values

        initMeetingRoomData(realm);
        initSampleSchedule(realm);
    }


    /**
     * initializing db with default meeting rooms
     * @param realm
     */
    void initMeetingRoomData(Realm realm){

        try{
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(new MeetingRoom(1,"001", "MR Council", "Phase 1", 6));
                    realm.insertOrUpdate(new MeetingRoom(2,"002", "MR Opera", "Phase 1", 6));
                    realm.insertOrUpdate(new MeetingRoom(3,"003", "MR Matrix", "Phase 1", 6));
                    realm.insertOrUpdate(new MeetingRoom(4,"004", "MR Alma matter", "Phase 1", 6));
                    realm.insertOrUpdate(new MeetingRoom(5,"005", "MR C1", "Phase 3", 5));
                    realm.insertOrUpdate(new MeetingRoom(6,"006", "MR C2", "Phase 3", 5));
                    realm.insertOrUpdate(new MeetingRoom(7,"007", "MR C3", "Phase 3", 5));
                }
            });
//            realm.beginTransaction();
//
//            realm.commitTransaction();
        }catch(RealmPrimaryKeyConstraintException exp){
            Toast.makeText(this,exp.getMessage(),Toast.LENGTH_SHORT).show();
        }catch(RealmMigrationNeededException exp){
            Toast.makeText(this,exp.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * initialize db with schedules for meeting room
     * @param realm
     */
    //no need
    void initSampleSchedule(Realm realm){

        try{

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    /*realm.insertOrUpdate(new Schedule(1,"Saravana","Software Engineer","Alti source","Scrum","Dec 13", "12:00" , "13:00",
                            realm.where(MeetingRoom.class).equalTo("mRoomName","MR Council").findFirst()));
                    realm.insertOrUpdate(new Schedule(2,"Srinath","Sr.Software Engineer","Alti source","Scrum","Dec 15","14:00","15:00",
                            realm.where(MeetingRoom.class).equalTo("mRoomName","MR Opera").findFirst()));
                    realm.insertOrUpdate(new Schedule(3,"Saravana","Software Engineer","Alti source","Scrum","Dec 17","12:00","13:30",
                            realm.where(MeetingRoom.class).equalTo("mRoomName","MR Opera").findFirst()));*/
                }
            });

        }catch(RealmException exp){

            Toast.makeText(this,exp.getMessage(),Toast.LENGTH_SHORT).show();

        }
//        realm.beginTransaction();
//
//
//        realm.commitTransaction();
    }
}
