package com.example.bwtools.android.tools.interfaces;

public interface KaKaOImp {
    void setupBaseURLAndRequestMethod();
    void setupKeyWord(String query);
    void setupAuthorization(String kakaoApiKey);
    void setupAutoClear(boolean doAutoClear);
    void startRequestQuery();
    String getResponse();
}
