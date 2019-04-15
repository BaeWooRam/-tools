package com.example.bwtools.android.tools.interfaces;

import com.napal.akginakwon.data.model.NaverRegion;

import java.util.ArrayList;

public interface NaverLocalImp extends NaverAPIlmp{
    void insertAllRegionDataAfterHandleResult();
    void insertRegionDataAfterHandleResult();
    ArrayList<NaverRegion> sortRegionArrayToList(ArrayList<NaverRegion> naverRegionList);
    ArrayList<NaverRegion> sortAllRegionArrayToList(ArrayList<NaverRegion> naverRegionList);
    ArrayList<NaverRegion> parserRegionArray(String result);
    int getRegionTotal(String result);
    int getRepetitionCount(int total);
    String getRegionSearchResult();
}
