package com.example.bwtools.android.tools.interfaces;

<<<<<<< HEAD
import com.example.bwtools.android.tools.base.dto.Location;
import com.example.bwtools.android.tools.base.dto.Rect;

public interface KaKaOLocalImp {
    void setupBaseURLAndRequestMethod(String baseURL, String requestMethod);
=======
import com.example.bwtools.android.tools.base.dto.KaKaORegion;
import com.example.bwtools.android.tools.base.dto.Location;
import com.example.bwtools.android.tools.base.dto.Rect;

import java.util.ArrayList;

public interface KaKaOLocalImp {
    void setupBaseURLAndRequestMethod();
>>>>>>> parent of cd21003... Revert "06-04"
    void setupKeyWord(String query);
    void setupKeyWordAndCategoryCode(String query, String categoryCode);
    void setupAuthorization(String kakaoApiKey);
    void setupAutoClear(boolean doAutoClear);
    void setupLocationRange(Location location, int radius);
    void setupLocationRange(Rect locationRange);
    void setupRequestOption(int pageNum, int displayCount, String sortMethod);
    void startRequestQuery();
<<<<<<< HEAD
    void HandleResponseAfterRequest();
=======
    ArrayList<KaKaORegion> getKaKaORegionList();
>>>>>>> parent of cd21003... Revert "06-04"
}
