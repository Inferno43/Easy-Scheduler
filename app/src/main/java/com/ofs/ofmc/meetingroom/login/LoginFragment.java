package com.ofs.ofmc.meetingroom.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.ofs.ofmc.meetingroom.BaseFragment;
import com.ofs.ofmc.meetingroom.R;
import com.ofs.ofmc.meetingroom.controllers.ViewController;
import com.ofs.ofmc.meetingroom.exceptions.EmptyTextException;
import com.ofs.ofmc.meetingroom.home.HomeActivity;
import com.ofs.ofmc.meetingroom.signup.SignupAcivity;
import com.ofs.ofmc.meetingroom.toolbox.Constants;
import com.ofs.ofmc.meetingroom.toolbox.MyProgressDialog;
import com.ofs.ofmc.meetingroom.toolbox.Utils;
import com.ofs.ofmc.meetingroom.toolbox.ViewUtils;

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



//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//
//                }else {
//
//                }
//            }
//        };
        return loginView.getRootView();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQ_SIGNUP && resultCode == Activity.RESULT_OK){
            userName.setText(data.getStringExtra(Constants.EXTRA_EMAIL));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setOnClickListener(View view) {

        int viewId = view.getId();

        switch (viewId){

            case R.id.submit:
                signIn(userName.getText().toString(),password.getText().toString());

                break;
            case R.id.forgotPassword:
                Snackbar.make(loginView.getRootView(),"Not implemented yet",Snackbar.LENGTH_LONG).show();
                break;
            case R.id.signup:
                startActivityForResult(new Intent(context,SignupAcivity.class), Constants.REQ_SIGNUP);
                //replaceActivity(SignupAcivity.class,true,null);
                break;
        }

    }

    private void signIn(String email, String password) {


        try {
            ViewUtils.getInstance().initProgress(getActivity(),"Logging in");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if(Utils.validateEmail(email) && Utils.validpassword(password)){
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    ViewUtils.getInstance().dismissProgress();
                                    Snackbar.make(loginView.getRootView(),task.getException().getMessage(),Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(loginView.getRootView(),e.getMessage(),Snackbar.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(getActivity(), new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(authResult.getUser()!=null){
                            Toast.makeText(context,"success",Toast.LENGTH_LONG).show();
                            replaceActivity(HomeActivity.class,true,null);
                            ViewUtils.getInstance().dismissProgress();
                        }

                    }
                });

            }else
                    Toast.makeText(context,"Invalid username or password",Toast.LENGTH_LONG).show();
            ViewUtils.getInstance().dismissProgress();
        } catch (EmptyTextException e) {

            Toast.makeText(context,userName.getHint()+"is empty",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }



}
