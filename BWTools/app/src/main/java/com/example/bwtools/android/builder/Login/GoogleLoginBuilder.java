package com.example.akginakwon.data.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.akginakwon.R;
import com.example.akginakwon.ui.base.BaseActivity;
import com.example.akginakwon.ui.base.BaseFragment;
import com.example.akginakwon.util.CommonUtils;
import com.example.akginakwon.util.ContractUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.annotations.NonNull;
import retrofit2.http.POST;

public class GoogleLoginBuilder {
    private static final String TAG = "GoogleLoginHelper";
    public static final int RC_GET_TOKEN = 9002;
    private BaseActivity mContext;
    private BaseFragment mFragment;
    private GoogleSignInClient mGoogleSignInClient;

    public GoogleSignInClient getGoogleSignInClient() {
        return mGoogleSignInClient;
    }

    public GoogleLoginBuilder setContext(BaseActivity Context) {
        this.mContext = Context;
        return this;
    }

    public GoogleLoginBuilder setFragment(BaseFragment fragment) {
        this.mFragment = fragment;
        return this;
    }


    public GoogleLoginBuilder build(){
        new Login();
        return this;
    }

    public void validateServerClientID() {
        String suffix = ".apps.googleusercontent.com";
        if (!mFragment.getString(R.string.server_client_id).trim().endsWith(suffix)) {
            String message = "Invalid server client ID in strings.xml, must end with " + suffix;

            Log.w(TAG, message);
            Toast.makeText(mFragment.getContext(),message,Toast.LENGTH_SHORT).show();
        }
    }

    public void getIdToken() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mFragment.startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    public void refreshIdToken() {
        mGoogleSignInClient.silentSignIn()
                .addOnCompleteListener(mContext, new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        handleSignInResult(task);
                    }
                });
    }

    // [START handle_sign_in_result]
    public GoogleSignInAccount handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        GoogleSignInAccount account = null;
        try {
            account = completedTask.getResult(ApiException.class);
        } catch (ApiException e) {
            Log.w(TAG, "handleSignInResult:error", e);
        }
        return  account;
    }
    // [END handle_sign_in_result]
    public GoogleLoginBuilder SignOut(OnCompleteListener<Void> signOutComplete,OnFailureListener signOutFailure) {
        mGoogleSignInClient.signOut().addOnCompleteListener(signOutComplete).addOnFailureListener(signOutFailure);
        return this;
    }


    public GoogleLoginBuilder RevokeAccess(OnCompleteListener<Void> revokeAccessComplete,OnFailureListener revokeAccessFailure) {
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(revokeAccessComplete).addOnFailureListener(revokeAccessFailure);
        return this;
    }

    //Login Class
    class Login{
        public Login() {
            if(mFragment != null && mContext != null) {
                GoogleLogin(mFragment.getContext());
            }else
                new Error(TAG+" Error! Please input context and fragment.");

        }
        private void GoogleLogin(Context context){
            validateServerClientID();

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.server_client_id))
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        }

    }
}
