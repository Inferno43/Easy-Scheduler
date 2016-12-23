package com.ofs.ofmc.meetingroom.profile;

import android.view.View;

import com.ofs.ofmc.meetingroom.ViewMvc;

import java.util.HashMap;

/**
 * Created by saravana.subramanian on 12/20/16.
 */

public interface ProfileView extends ViewMvc {

    interface ClickListener{

        void setClickListener(View view);
        void onSaveClickListener(View view, HashMap<String,String> profiemap);

    }

    void setListener(ClickListener clickListener);
}
