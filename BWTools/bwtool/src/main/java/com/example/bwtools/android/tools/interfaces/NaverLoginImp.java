package com.example.bwtools.android.tools.interfaces;

import com.nhn.android.naverlogin.OAuthLogin;

public interface NaverLoginImp extends NaverAPIlmp {
    //세팅
    OAuthLogin getOAuthLoginInstance();
    void setupNaverOAuthLoginInstance(OAuthLogin OAuthLoginInstance);
    void setupNaverOAuthClientInfo();

    //동작
    void logOut();
    void RequestUserInfo();
    void RefreshUserInfo();
    void Disconnect();
    void LogOutAfterUI();
    void RequestUserInfoAfterUI(String account);
}
