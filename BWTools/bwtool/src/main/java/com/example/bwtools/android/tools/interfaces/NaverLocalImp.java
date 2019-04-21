package com.example.bwtools.android.tools.interfaces;

<<<<<<< HEAD
public interface NaverLocalImp extends NaverAPIlmp{
    void insertRegionData();
    void AddRegionData();
=======
import com.example.bwtools.android.tools.base.dto.NaverRegion;

import java.util.ArrayList;

public interface NaverLocalImp extends NaverAPIlmp {
    void insertRegionData();
    void AddRegionData();
    void restartParserRegionArray(ArrayList<NaverRegion> naverRegionsList);
    ArrayList<NaverRegion> getParserRegionList(String result);
>>>>>>> 951065f76a4ea85262ca59f3953d21c395a4dd0a
    String getRegionSearchResult();
}
