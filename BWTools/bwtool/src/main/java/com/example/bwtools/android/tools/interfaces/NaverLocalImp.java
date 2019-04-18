package com.example.bwtools.android.tools.interfaces;

import com.example.bwtools.android.tools.base.dto.NaverRegion;

import java.util.ArrayList;

public interface NaverLocalImp extends NaverAPIlmp {
    void insertRegionData();
    void AddRegionData();
    void restartParserRegionArray(ArrayList<NaverRegion> naverRegionsList);
    ArrayList<NaverRegion> getParserRegionList(String result);
    String getRegionSearchResult();
}
