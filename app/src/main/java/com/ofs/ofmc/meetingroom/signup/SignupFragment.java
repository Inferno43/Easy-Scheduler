package com.ofs.ofmc.meetingroom.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ofs.ofmc.meetingroom.BaseFragment;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.ViewController;
import com.ofs.ofmc.meetingroom.exceptions.EmptyTextException;
import com.ofs.ofmc.meetingroom.login.LoginActivity;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.Logr;
import com.ofs.ofmc.meetingroom.toolbox.Utils;
import com.ofs.ofmc.meetingroom.toolbox.ViewUtils;

import java.io.Serializable;

/**
 * Created by saravana.subramanian on 1/4/17.
 */

public class SignupFragment extends BaseFragment implements ViewController.ClickListener{
    Context context;
    ViewController signupView;
    EditText userEmail;
    EditText password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        signupView = new SignupImplView(inflater,container,null);
        signupView.setListener(this);
        context = signupView.getRootView().getContext();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                }else {

                }
            }
        };

        userEmail = (EditText)signupView.getRootView().findViewById(R.id.userEmail);
        password = (EditText)signupView.getRootView().findViewById(R.id.password);

        return signupView.getRootView();
    }

    @Override
    public void setOnClickListener(View view) {
        int viewId = view.getId();

        switch(viewId){
            case R.id.register:
                ViewUtils.getInstance().initProgress(getActivity(),"Signing Up");
                createAccount(userEmail.getText().toString() ,password.getText().toString());
                break;

        }

    }

    private void createAccount(String email, String password) {
        try {
            Logr.d(""+Utils.validateEmail(email) +" "+ Utils.validpassword(password));
            if(Utils.validateEmail(email) && Utils.validpassword(password)){
                //replaceActivity(LoginActivity.class,true,null);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {

                                    //Toast.makeText(context,"Could not create your account, please try later",Toast.LENGTH_LONG).show();
                                }else{
                                    task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            if(authResult.getUser()!=null){
                                                Toast.makeText(context,"success",Toast.LENGTH_LONG).show();
                                                ViewUtils.getInstance().dismissProgress();
                                                Intent authArgs = new Intent();
                                                authArgs.putExtra(Constants.EXTRA_EMAIL,authResult.getUser().getEmail());
                                                getActivity().setResult(Activity.RESULT_OK,authArgs);
                                                getActivity().finish();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(getActivity(), new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        });
            }else
                Toast.makeText(context,"Invalid email or password",Toast.LENGTH_LONG).show();
        } catch (EmptyTextException e) {

            Toast.makeText(context,userEmail.getHint()+"is empty",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
