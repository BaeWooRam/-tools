package com.example.bwtools.android.tools.base;

import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

import androidx.annotation.NonNull;

public abstract class BaseNaverMapActivity extends BaseActivity implements OnMapReadyCallback {

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        NaverMapReady(naverMap);
    }


    public abstract void NaverMapReady(NaverMap naverMap);

}
