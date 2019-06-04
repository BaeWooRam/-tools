package com.example.bwtools.android.tools.imp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.example.bwtools.android.tools.interfaces.NaverMaplmp;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;


public class FactoryNaverMap implements NaverMaplmp {
    private final String TAG = "FactoryNaverLogin";
    private final int INT_MAX = 2147483647;

    private Activity targetActivity;
    private NaverMap targetNaverMap;
    private Map<String, OverlayImage> markerOverlayImageMap;
    private Map<Object, Marker> markerMap;
    private static MapFragment naverMapFragment;

    private int MarkerID = 0;

    public FactoryNaverMap(@NonNull Activity thisActivity, @NonNull NaverMap thisNaverMap) {
        this.targetActivity = thisActivity;
        targetNaverMap = thisNaverMap;
        markerOverlayImageMap = new HashMap<>();
        markerMap = new HashMap<>();
    }

    public static void setupNaverMap(FragmentManager fragmentManager, OnMapReadyCallback mapReadyCallback, @IdRes int fragmentMapID) {
        findByNaverMapAndFragmentCommit(fragmentManager, fragmentMapID);
        naverMapFragment.getMapAsync(mapReadyCallback);
    }

    private static void findByNaverMapAndFragmentCommit(FragmentManager fragmentManager, @IdRes int fragmentMapID) {
        naverMapFragment = (MapFragment) fragmentManager.findFragmentById(fragmentMapID);
        if (naverMapFragment == null) {
            naverMapFragment = MapFragment.newInstance();
            fragmentManager.beginTransaction().add(fragmentMapID, naverMapFragment).commit();
        }
    }

    public void onViewCreated(@Nullable Bundle savedInstanceState) {
        if (naverMapFragment == null)
            naverMapFragment.onCreate(savedInstanceState);
    }

    public void onFragmentStart() {
        if (naverMapFragment == null)
            naverMapFragment.onStart();
    }

    public void onFragmentResume() {
        if (naverMapFragment == null)
            naverMapFragment.onResume();
    }

    public void onFragmentPause() {
        if (naverMapFragment == null)
            naverMapFragment.onPause();
    }

    public void onFragmentSaveInstanceState(Bundle outState) {
        if (naverMapFragment == null)
            naverMapFragment.onSaveInstanceState(outState);
    }

    public void onFragmentStop() {
        if (naverMapFragment == null)
            naverMapFragment.onStop();
    }

    public void onFragmentDestroyView() {
        if (naverMapFragment == null)
            naverMapFragment.onDestroy();
    }

    public void onFragmentLowMemory() {
        if (naverMapFragment == null)
            naverMapFragment.onLowMemory();
    }
    //맵 상에서 보여줄 것들에 대한 세팅

    /**
     * 마커에 오버레이 이미지를 사용하기 위해서는 여기서 등록해야한다.(Resource)
     */
    public void setupMarkerOverlayImageFromResource(@NonNull String key, @DrawableRes int resource) {
        OverlayImage markerImage = OverlayImage.fromResource(resource);
        markerOverlayImageMap.put(key, markerImage);
    }

    /**
     * 마커에 오버레이 이미지를 사용하기 위해서는 여기서 등록해야한다.(Bitmap)
     */
    public void setupMarkerOverlayImageFromBitmap(@NonNull String key, @NonNull Bitmap resource) {
        OverlayImage markerImage = OverlayImage.fromBitmap(resource);
        markerOverlayImageMap.put(key, markerImage);
    }

    /**
     * 마커에 오버레이 이미지를 사용하기 위해서는 여기서 등록해야한다.(View)
     */
    public void setupMarkerOverlayImageFromView(@NonNull String key, @NonNull View resource) {
        OverlayImage markerImage = OverlayImage.fromView(resource);
        markerOverlayImageMap.put(key, markerImage);
    }

    public void removeMarkerOverlayImage(@NonNull String key) {
        markerOverlayImageMap.remove(key);
    }

    public void removeAllMarker() {
        List<Marker> list = new ArrayList<>(markerMap.values());

        for (Marker targetMarker : list) {
            targetMarker.setMap(null);
        }
    }

    public void removeTargetMarker(@NonNull Object tag) {
        Marker targetMarker = markerMap.get(tag);
        targetMarker.setMap(null);
    }

    /**
     * 마커 태그는 유니크한 것으로 지정하세요.
     * 마커에 오버레이 이미지를 사용하기 위해서는 setupMarkerOverlayImage에서 먼저 등록해야한다.
     */
    public void viewNaverMapMarker(String overlayImageKey, Marker mapMarker, Marker.OnClickListener markerClickListener) {
        OverlayImage targetOverlayImage = markerOverlayImageMap.get(overlayImageKey);
        try {
            markerMap.put(mapMarker.getTag(), mapMarker);

            mapMarker.setOnClickListener(markerClickListener);
            mapMarker.setIcon(targetOverlayImage);
            mapMarker.setMap(targetNaverMap);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    /**
     * 마커 태그는 유니크한 것으로 지정하세요.
     */
    public void viewNaverMapMarker(Marker mapMarker, Marker.OnClickListener markerClickListener) {
        try {
            markerMap.put(mapMarker.getTag(), mapMarker);

            mapMarker.setOnClickListener(markerClickListener);
            mapMarker.setMap(targetNaverMap);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public void viewNaverMapMarker(Marker mapMarker) {
        try {
            markerMap.put(mapMarker.getTag(), mapMarker);

            mapMarker.setMap(targetNaverMap);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }


    public void veiwNaverMapMarkerArray(String overlayImageKey, Marker[] mapMarkerArray, Marker.OnClickListener markerClickListener) {
        OverlayImage targetOverlayImage = markerOverlayImageMap.get(overlayImageKey);
        try {
            for (Marker mapMarker : mapMarkerArray) {
                mapMarker.setOnClickListener(markerClickListener);
                mapMarker.setIcon(targetOverlayImage);
                mapMarker.setMap(targetNaverMap);
            }
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    @Override
    public void viewNaverMapMarkerInfo() {

    }


    private void settingNaverMapActivty() {

    }


    public void settingNaverMapType(NaverMap.MapType mapType) {
        try {
            targetNaverMap.setMapType(mapType);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public void settingNaverMapLightness(float lightness) {
        try {
            targetNaverMap.setLightness(lightness);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public void settingNaverMapSymbolScale(float symbolScale) {
        try {
            targetNaverMap.setSymbolScale(symbolScale);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public void settingNaverMapAnimateCameraMove(LatLng moveLatLng, CameraAnimation cameraAnimation) {
        try {
            CameraUpdate targetCameraUpdate = CameraUpdate.scrollTo(moveLatLng).animate(cameraAnimation);
            targetNaverMap.moveCamera(targetCameraUpdate);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public void settingNaverMapOptionListener(NaverMap.OnOptionChangeListener onOptionChangeListener) {
        try {
            targetNaverMap.addOnOptionChangeListener(onOptionChangeListener);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public void settingCameraChangeListener(NaverMap.OnCameraChangeListener onCameraChangeListener) {
        try {
            targetNaverMap.addOnCameraChangeListener(onCameraChangeListener);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public void settingNaverMapZoomControl(boolean enable) {
        try {
            UiSettings uiSettings = targetNaverMap.getUiSettings();
            uiSettings.setZoomControlEnabled(enable);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public void settingNaverMapLocationButton(boolean enable) {
        try {
            UiSettings uiSettings = targetNaverMap.getUiSettings();
            uiSettings.setLocationButtonEnabled(enable);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }


    public void settingOnMapClickListener(NaverMap.OnMapClickListener naverMapClickListener) {
        try {
            targetNaverMap.setOnMapClickListener(naverMapClickListener);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public void settingOnOverlayClickListener(Overlay.OnClickListener naverOverlayClickListener) {

    }

    public void settingOnMapLongClickListener(NaverMap.OnMapLongClickListener naverMapLongClickListener) {
        try {

        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public Marker createNaverMapMarker(@NonNull Object makerTag, @NonNull String makerContents, @NonNull LatLng makerPostion) {
        Marker newMarker = new Marker();
        newMarker.setPosition(makerPostion);
        newMarker.setTag(makerTag);
        newMarker.setCaptionText(makerContents);

        return newMarker;
    }

    public Marker createNaverMapMarker(@NonNull String makerContents, @NonNull LatLng makerPostion) {
        Marker newMarker = new Marker();
        newMarker.setPosition(makerPostion);
        newMarker.setTag(MarkerID);
        newMarker.setCaptionText(makerContents);
        incrementMarkerID();
        return newMarker;
    }

    private void incrementMarkerID() {
        int NextID = MarkerID + 1;
        if (NextID == INT_MAX)
            new Error("Don't create NaverMapMaker");
        else
            MarkerID++;
    }

    public boolean isCreateMarker() {
        return !markerMap.isEmpty();
    }
}
