package com.ofs.ofmc.meetingroom;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;

import com.ofs.ofmc.meetingroom.callbacks.AbstractActivityCallback;
import com.ofs.ofmc.meetingroom.callbacks.AbstractFragmentCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {


        private AbstractFragmentCallback mCallback;
        private AbstractActivityCallback mActivityCallback;

        @Override
        public void onAttach(Activity activity) {
                super.onAttach(activity);
                setHasOptionsMenu(true);

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




}
