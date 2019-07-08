package com.example.bwtools.android.tools.imp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.bwtools.R;
import com.example.bwtools.android.tools.interfaces.GoogleLoginImp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class FactoryGoogleLogin implements GoogleLoginImp {
    private final String TAG = "FactoryGoogleLogin";
    public final int RC_GET_TOKEN = 9002;
    private Activity targetActivity;
    private Fragment targetFragment;
    private com.google.android.gms.auth.api.signin.GoogleSignInClient GoogleSignInClient;
    private GoogleSignInOptions googleSignInOptions;

    public FactoryGoogleLogin(Activity thisActivity, Fragment thisFragment) {
        this.targetActivity = thisActivity;
        this.targetFragment = thisFragment;
    }

    @Override
    public String getServerID() {
         return targetActivity.getString(R.string.server_client_id);
    }

    @Override
    public void checkServerClientID() {
        String suffix = ".apps.googleusercontent.com";
        if (!targetActivity.getString(R.string.server_client_id).trim().endsWith(suffix)) {
            String message = "Invalid server client ID in strings.xml, must end with " + suffix;

            new Error(TAG+message);
        }
    }

    @Override
    public void setupServerOptions() {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getServerID())
                .requestEmail()
                .build();
    }

    /**
     *  로그인을 위한 정보들 세팅
     */
    @Override
    public void setupGoogleSignClient() {
        checkServerClientID();
        setupServerOptions();
        GoogleSignInClient = GoogleSignIn.getClient(targetActivity, googleSignInOptions);
    }


    /**
     *  로그인을 위한 idToken을 구글서버로부터 받아온다.
     */
    @Override
    public void getIDToken() {
        Intent signInIntent = GoogleSignInClient.getSignInIntent();
        targetFragment.startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    /**
     *  로그인을 위한 idToken을 구글서버로부터 갱신시킨다.
     */
    @Override
    public void refreshIDToken() {
        GoogleSignInClient.silentSignIn()
                .addOnCompleteListener(targetActivity, new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        handleGetIDTokenResult(task);
                    }
                });
    }

    /**
     *  구글서버로부터 받은 응답에 대한 이벤트 Handle
     *   @param completedTask 구글서버로부터 가져온 로그인 관련 정보들
     */
    @Override
    public GoogleSignInAccount handleGetIDTokenResult(Task<GoogleSignInAccount> completedTask) {
        GoogleSignInAccount account = null;
        try {
            account = completedTask.getResult(ApiException.class);
        } catch (ApiException e) {
            Log.w(TAG, "handleSignInResult:error", e);
        }
        return  account;
    }

    /**
     *  로그아웃 후, 이벤트 처리
     */
    @Override
    public void signOut(OnCompleteListener<Void> signOutCompleteHandler, OnFailureListener signOutFailureHandler) {
        GoogleSignInClient.signOut().addOnCompleteListener(signOutCompleteHandler).addOnFailureListener(signOutFailureHandler);
    }

    /**
     *  응용 프로그램에 대한 액세스 권한 취소 후, 이벤트 처리(로그인 연동 끊는거)
     */
    @Override
    public void revokeAccess(OnCompleteListener<Void> revokeAccessCompleteHandler, OnFailureListener revokeAccessFailureHandler) {
        GoogleSignInClient.revokeAccess().addOnCompleteListener(revokeAccessCompleteHandler).addOnFailureListener(revokeAccessFailureHandler);
    }
}
