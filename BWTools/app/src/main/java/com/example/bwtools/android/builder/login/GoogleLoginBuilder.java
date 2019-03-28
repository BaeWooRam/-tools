package com.example.bwtools.android.builder.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

import android.widget.Toast;

import com.example.bwtools.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class GoogleLoginBuilder {
    private static final String TAG = "GoogleLoginHelper";
    public static final int RC_GET_TOKEN = 9002;
    private Activity mContext;
    private Fragment mFragment;
    private GoogleSignInClient mGoogleSignInClient;

    public GoogleSignInClient getGoogleSignInClient() {
        return mGoogleSignInClient;
    }

    public GoogleLoginBuilder setContext(Activity Context) {
        this.mContext = Context;
        return this;
    }

    public GoogleLoginBuilder setFragment(Fragment fragment) {
        this.mFragment = fragment;
        return this;
    }


    public GoogleLoginBuilder build(){
        new Login();
        return this;
    }

    /**
     *  client 정보를 패턴 유효성 체크
     */
    public void validateServerClientID() {
        String suffix = ".apps.googleusercontent.com";
        if (!mFragment.getString(R.string.server_client_id).trim().endsWith(suffix)) {
            String message = "Invalid server client ID in strings.xml, must end with " + suffix;

            Log.w(TAG, message);
            Toast.makeText(mFragment.getContext(),message,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *  로그인을 위한 idToken을 구글서버로부터 받아온다.
     */
    public void getIdToken() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mFragment.startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    /**
     *  로그인을 위한 idToken을 구글서버로부터 갱신시킨다.
     */
    public void refreshIdToken() {
        mGoogleSignInClient.silentSignIn()
                .addOnCompleteListener(mContext, new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        handleSignInResult(task);
                    }
                });
    }

    /**
     *  구글서버로부터 받은 응답에 대한 이벤트 Handle
     *   @param completedTask 구글서버로부터 가져온 로그인 관련 정보들
     */

    public GoogleSignInAccount handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
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
     *  @param signOutComplete 로그아웃 성공시 이벤트 처리
     *  @param signOutFailure 로그아웃 실패시 이벤트 처리
     */
    public GoogleLoginBuilder SignOut(OnCompleteListener<Void> signOutComplete,OnFailureListener signOutFailure) {
        mGoogleSignInClient.signOut().addOnCompleteListener(signOutComplete).addOnFailureListener(signOutFailure);
        return this;
    }

    /**
     *  응용 프로그램에 대한 액세스 권한 취소 후, 이벤트 처리(로그인 연동 끊는거)
     *  @param revokeAccessComplete 액세서 권한 취소 성공시 이벤트 처리
     *  @param revokeAccessFailure 액세서 권한 취소 실패시 이벤트 처리
     */
    public GoogleLoginBuilder RevokeAccess(OnCompleteListener<Void> revokeAccessComplete,OnFailureListener revokeAccessFailure) {
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(revokeAccessComplete).addOnFailureListener(revokeAccessFailure);
        return this;
    }

    /**
     *  빌더 패턴을 위한 로그인 객체
     */
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
