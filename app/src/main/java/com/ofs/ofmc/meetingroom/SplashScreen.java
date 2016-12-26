package com.ofs.ofmc.meetingroom;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ofs.ofmc.meetingroom.login.LoginFragment;

/**
 * Created by saravana.subramanian on 12/1/16.
 *
 * Splash screen fragment
 */

public class SplashScreen extends BaseFragment{


    View rootView;
    Handler mHandler;
    Runnable mRunnable;
    ShimmerFrameLayout shimmmerContainer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.splash_screen,null);

        shimmmerContainer =(ShimmerFrameLayout) rootView.findViewById(R.id.shimmer_view_container);
        shimmmerContainer.startShimmerAnimation();

        mHandler = new Handler();
        mRunnable  = new Runnable() {
            @Override
            public void run() {

                replaceFragment(LoginFragment.class,false,null);


            }
        };mHandler.postDelayed(mRunnable,3000);

        return rootView;
    }

    @Override
    public void onPause() {
        shimmmerContainer.stopShimmerAnimation();
        super.onPause();
    }
}
