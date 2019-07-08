package com.example.bwtools.android.tools.interfaces;

public interface AutoAddressImp {
    void setupBaseURLAndRequestMethod();
    void setupKeyWord(String keyword);
    void setupAuthorization(String confirmKey);
    void setupAutoClear(boolean doAutoClear);
    void setupRequestOption(int pageNum, int displayCount, String resultType);
    void startRequestQuery();
    String getResponse();
}
