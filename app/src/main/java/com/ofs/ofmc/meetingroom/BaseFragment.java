package com.ofs.ofmc.meetingroom;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ofs.ofmc.meetingroom.callbacks.AbstractActivityCallback;
import com.ofs.ofmc.meetingroom.callbacks.AbstractFragmentCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {


        private AbstractFragmentCallback mCallback;
        private AbstractActivityCallback mActivityCallback;
        protected FirebaseAuth mAuth;
        protected FirebaseAuth.AuthStateListener mAuthListener;
        protected FirebaseUser user;
        protected FirebaseDatabase firebaseDatabase;

        @Override
        public void onAttach(Activity activity) {
                super.onAttach(activity);
                setHasOptionsMenu(true);
                mAuth = FirebaseAuth.getInstance();
                firebaseDatabase = FirebaseDatabase.getInstance();
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                user = firebaseAuth.getCurrentUser();
                                if (user != null) {

                                }else {

                                }
                        }
                };

                try {
                        mCallback = (AbstractFragmentCallback) activity;
                        mActivityCallback = (AbstractActivityCallback) activity;
                } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                + " must implement " + AbstractFragmentCallback.class.getCanonicalName());
                }

                }

        /**
         * This method replaces the currently shown fragment with a new fragment of a particular class.
         * If a fragment of the required class already shown - does nothing.
         * @param claz the class of the fragment to show
         * @param addToBackStack whether the replacement should be added to back-stack
         * @param args arguments for the newly created fragment (can be null)
         */
        public void replaceFragment(Class<? extends Fragment> claz, boolean addToBackStack,
                Bundle args) {
                mCallback.replaceFragment(claz, addToBackStack, args);
                }


        public void replaceActivity(Class<? extends Activity> claz, boolean addToBackStack, Bundle args) {

                mActivityCallback.replaceActivity(claz,addToBackStack,args);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
                // TODO Add your menu entries here
                super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public void onStart() {
                super.onStart();
                mAuth.addAuthStateListener(mAuthListener);
        }

        @Override
        public void onStop() {
                super.onStop();
                if (mAuthListener != null) {
                        mAuth.removeAuthStateListener(mAuthListener);
                }
        }




}
