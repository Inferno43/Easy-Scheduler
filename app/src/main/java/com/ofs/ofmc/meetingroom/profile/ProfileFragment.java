package com.ofs.ofmc.meetingroom.profile;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ofs.ofmc.meetingroom.BaseFragment;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.exceptions.EmptyTextException;
import com.ofs.ofmc.meetingroom.model.Profile;
import com.ofs.ofmc.meetingroom.settings.SettingsImplView;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.SharedPref;

import java.util.HashMap;

import io.realm.Realm;

/**
 * Created by saravana.subramanian on 12/20/16.
 */

public class ProfileFragment extends BaseFragment implements ProfileView.ClickListener{

    private SharedPref sharedPref;
    private Context context;
    private ProfileView profileImplView;
    Realm realm;
    public ProfileFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileImplView = new ProfileImplView(inflater,container);
        profileImplView.setListener(this);
        sharedPref = new SharedPref();
        realm = Realm.getDefaultInstance();
        context = getActivity();
        return profileImplView.getRootView();
    }
    @Override
    public void setClickListener(View view) {

    }

    @Override
    public void onSaveClickListener(View view, final HashMap<String, String> profiemap) {
        int viewId = view.getId();
        switch(viewId){
            case R.id.save:
                try{
                    if(profiemap.get(Constants.EMPLOYEE_NAME).isEmpty() && profiemap.get(Constants.EMPLOYEE_ID).isEmpty() &&
                            profiemap.get(Constants.EMPLOYEE_DESIGNATION).isEmpty() && profiemap.get(Constants.EMPLOYEE_TEAM).isEmpty()
                            && profiemap.get(Constants.EMPLOYEE_PHASE).isEmpty()){

                        throw new EmptyTextException("please fill all fields");

                    }else{
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.insertOrUpdate(new Profile(1,profiemap.get(Constants.EMPLOYEE_NAME),
                                        profiemap.get(Constants.EMPLOYEE_ID),profiemap.get(Constants.EMPLOYEE_DESIGNATION),
                                        profiemap.get(Constants.EMPLOYEE_TEAM),profiemap.get(Constants.EMPLOYEE_PHASE)));
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                Snackbar.make(profileImplView.getRootView(),"Saved your profile successfully",Snackbar.LENGTH_LONG).show();
                                Handler handler = new Handler();
                                handler.postDelayed (new Runnable() {
                                    @Override
                                    public void run() {
                                        getActivity().finish();
                                    }
                                },1000);

                            }
                        });
                    }

                }catch (EmptyTextException e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
