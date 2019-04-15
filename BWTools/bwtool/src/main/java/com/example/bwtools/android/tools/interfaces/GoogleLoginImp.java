package com.example.bwtools.android.tools.interfaces;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import io.reactivex.annotations.NonNull;

public interface GoogleLoginImp extends GoogleAPIlmp{
    void setupGoogleSignClient();
    void setupServerOptions();
    void getIDToken();
    void refreshIDToken();
    GoogleSignInAccount handleGetIDTokenResult(@NonNull Task<GoogleSignInAccount> completedTask);
    void signOut(OnCompleteListener<Void> signOutComplete, OnFailureListener signOutFailure);
    void revokeAccess(OnCompleteListener<Void> revokeAccessComplete, OnFailureListener revokeAccessFailure);
}
