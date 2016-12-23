package com.ofs.ofmc.meetingroom.login;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ofs.ofmc.meetingroom.BaseFragment;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.ViewController;
import com.ofs.ofmc.meetingroom.exceptions.EmptyTextException;
import com.ofs.ofmc.meetingroom.home.HomeActivity;
import com.ofs.ofmc.meetingroom.toolbox.Utils;

/**
 * Created by saravana.subramanian on 11/29/16.
 */

public class LoginFragment extends BaseFragment implements ViewController.ClickListener{

    Context context;
    ViewController loginView;
    EditText userName;
    EditText password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        loginView = new LoginImplView(inflater,container);
        loginView.setListener(this);
        context = loginView.getRootView().getContext();

        userName = (EditText)loginView.getRootView().findViewById(R.id.userName);
        password = (EditText)loginView.getRootView().findViewById(R.id.password);


        return loginView.getRootView();
    }

    @Override
    public void setOnClickListener(View view) {

        int viewId = view.getId();

        switch (viewId){

            case R.id.submit:
                try {
                    if(Utils.getInstance().validUserName(userName.getText().toString())
                            && Utils.getInstance().validpassword(password.getText().toString())){
                        Toast.makeText(context,"success",Toast.LENGTH_LONG).show();

                        replaceActivity(HomeActivity.class,true,null);
                    }else
                        Toast.makeText(context,"Invalid username or password",Toast.LENGTH_LONG).show();
                } catch (EmptyTextException e) {

                    Toast.makeText(context,userName.getHint()+"is empty",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.forgotPassword:
                Snackbar.make(loginView.getRootView(),"Not implemented yet",Snackbar.LENGTH_LONG).show();
                break;
        }

    }
}
