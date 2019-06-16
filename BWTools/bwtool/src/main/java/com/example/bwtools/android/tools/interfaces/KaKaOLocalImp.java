package com.example.bwtools.android.tools.interfaces;

import com.example.bwtools.android.tools.base.dto.Location;
import com.example.bwtools.android.tools.base.dto.Rect;

public interface KaKaOLocalImp {
    void setupBaseURLAndRequestMethod();
    void setupKeyWord(String query);
    void setupKeyWordAndCategoryCode(String query, String categoryCode);
    void setupAuthorization(String kakaoApiKey);
    void setupAutoClear(boolean doAutoClear);
    void setupLocationRange(Location location, int radius);
    void setupLocationRange(Rect locationRange);
    void setupRequestOption(int pageNum, int displayCount, String sortMethod);
    void startRequestQuery();
    String getResponse();
}
