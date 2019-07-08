package com.example.bwtools.android.tools.interfaces;

import com.example.bwtools.android.tools.dto.Point;
import com.example.bwtools.android.tools.dto.Rect;

public interface KaKaOLocalImp extends KaKaOImp {
    void setupKeyWordAndCategoryCode(String query, String categoryCode);
    void setupLocationRange(Point location, int radius);
    void setupLocationRange(Rect locationRange);
    void setupRequestOption(int pageNum, int displayCount, String sortMethod);
}
