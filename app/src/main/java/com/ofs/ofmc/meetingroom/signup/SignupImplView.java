package com.ofs.ofmc.meetingroom.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.ViewController;

/**
 * Created by saravana.subramanian on 1/4/17.
 */

public class SignupImplView implements ViewController {

    private View mRootView;
    private ClickListener mClickListener;
    private Button mRegister;

    public SignupImplView(LayoutInflater inflater, ViewGroup container,Bundle args) {
        mRootView = inflater.inflate(R.layout.mvc_view_signup, container, false);
        mRegister = (Button)mRootView.findViewById(R.id.register);

        mRegister.setOnClickListener(new View.OnClickListener() {
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
