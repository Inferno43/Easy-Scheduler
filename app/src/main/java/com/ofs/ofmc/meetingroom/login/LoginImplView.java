package com.ofs.ofmc.meetingroom.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.ViewController;

/**
 * Created by saravana.subramanian on 11/29/16.
 */

public class LoginImplView implements ViewController {

    private View mRootView;
    private ClickListener mClickListener;
    private Button mLogIn;
    private Button mSignUp;
    private TextView mForgotPassword;


    LoginImplView(LayoutInflater inflater, ViewGroup container){
        mRootView = inflater.inflate(R.layout.mvc_view_login, container, false);
        mLogIn = (Button)mRootView.findViewById(R.id.submit);
        mSignUp = (Button)mRootView.findViewById(R.id.signup);
        mForgotPassword = (TextView) mRootView.findViewById(R.id.forgotPassword);

        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener!=null){
                    mClickListener.setOnClickListener(view);
                }
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener!=null){
                    mClickListener.setOnClickListener(view);
                }
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener!=null){
                    mClickListener.setOnClickListener(view);
                }
            }
        });

    }



    @Override
    public void setListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }
}
