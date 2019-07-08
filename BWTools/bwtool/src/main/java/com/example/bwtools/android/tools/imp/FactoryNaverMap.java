package com.example.bwtools.android.tools.imp;

import android.graphics.Bitmap;
import android.view.View;

import com.example.bwtools.android.tools.interfaces.NaverMaplmp;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
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
import androidx.fragment.app.FragmentManager;


public class FactoryNaverMap implements NaverMaplmp {
    private final String TAG = "FactoryNaverLogin";
    private final int INT_MAX = 2147483647;

    public final int IMPORTANT_HIGH = 3;
    public final int IMPORTANT_NORMAL = 2;
    public final int IMPORTANT_LOW = 1;

    private NaverMap targetNaverMap;
    private Map<String, OverlayImage> markerOverlayImageMap;
    private Map<Object, Marker> markerMap;
    private static MapFragment naverMapFragment;

    private List<Object> importantHighMarkers;
    private List<Object> importantLowMarkers;

    private int MarkerID = 0;

    public FactoryNaverMap() {
        markerOverlayImageMap = new HashMap<>();
        markerMap = new HashMap<>();
        importantLowMarkers = new ArrayList<>();
        importantHighMarkers = new ArrayList<>();
    }

    public void setNaverMap(NaverMap targetNaverMap){
        this.targetNaverMap = targetNaverMap;
    }

    public void setupNaverMap(FragmentManager fragmentManager, OnMapReadyCallback mapReadyCallback, @IdRes int fragmentMapID) {
        findByNaverMapAndFragmentCommit(fragmentManager, fragmentMapID);
        naverMapFragment.getMapAsync(mapReadyCallback);
    }

    private void findByNaverMapAndFragmentCommit(FragmentManager fragmentManager, @IdRes int fragmentMapID) {
        naverMapFragment = (MapFragment) fragmentManager.findFragmentById(fragmentMapID);
        if (naverMapFragment == null) {
            naverMapFragment = MapFragment.newInstance();
            fragmentManager.beginTransaction().add(fragmentMapID, naverMapFragment).commit();
        }
    }


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

        if(targetMarker != null)
            targetMarker.setMap(null);
    }

    /**
     * 마커 태그는 유니크한 것으로 지정하세요.
     * 마커에 오버레이 이미지를 사용하기 위해서는 setupMarkerOverlayImage에서 먼저 등록해야한다.
     */
    public void showMarker(String overlayImageKey, Marker mapMarker, Marker.OnClickListener markerClickListener) {
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
    public void showMarker(Marker mapMarker, Marker.OnClickListener markerClickListener) {
        try {
            markerMap.put(mapMarker.getTag(), mapMarker);

            mapMarker.setOnClickListener(markerClickListener);
            mapMarker.setMap(targetNaverMap);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public void showMarker(Marker mapMarker) {
        try {
            markerMap.put(mapMarker.getTag(), mapMarker);

            mapMarker.setMap(targetNaverMap);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }


    public void showMarkerArray(String overlayImageKey, Marker[] mapMarkerArray, Marker.OnClickListener markerClickListener) {
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

    public void moveCameraAnimate(LatLng moveLatLng, CameraAnimation cameraAnimation) {
        try {
            CameraUpdate targetCameraUpdate = CameraUpdate.scrollTo(moveLatLng).animate(cameraAnimation);
            targetNaverMap.moveCamera(targetCameraUpdate);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    /**
     *
     * @param moveLatLng 위도,경도
     * @param zoom 축척 레벨
     */
    public void moveCameraAnimate(LatLng moveLatLng, double zoom) {
        try {
            CameraPosition cameraPosition = new CameraPosition(
                    moveLatLng,
                    zoom
            );
            targetNaverMap.setCameraPosition(cameraPosition);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }



    public void settingOptionListener(NaverMap.OnOptionChangeListener onOptionChangeListener) {
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

    public void settingOnMapLongClickListener(NaverMap.OnMapLongClickListener naverMapLongClickListener) {
        try {
            targetNaverMap.setOnMapLongClickListener(naverMapLongClickListener);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public Marker createNaverMapMarker(@NonNull Object makerTag, @NonNull String makerContents, @NonNull LatLng makerPostion, Overlay.OnClickListener naverOverlayClickListener) {
        Marker newMarker = new Marker();
        newMarker.setPosition(makerPostion);
        newMarker.setTag(makerTag);
        newMarker.setCaptionText(makerContents);
        newMarker.setOnClickListener(naverOverlayClickListener);
        return newMarker;
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

    public void transformImportantMarkers(@NonNull Object makerTag, int Important){
        Marker marker = getMarker(makerTag);
        marker.setZIndex(Important);
        marker.setForceShowIcon(true);
        showMarker(marker);
    }

    public void transformImportantHighOnlyThisMarker(@NonNull Object makerTag){
        removeImportantHighMarker();
        transformImportantHighMarker(makerTag);
    }

    public void transformImportantLowOnlyThisMarker(@NonNull Object makerTag){
        removeImportantLowMarker();
        transformImportantLowMarker(makerTag);
    }

    public void removeImportantHighMarker(){

        //important High였던 마크들을 다시 Normarl로 바꾼다.
        for(Object tag : importantHighMarkers){
            transformImportantNormalMarker(tag);
        }

        //important High의 tag값을 저장했던 배열도 초기화한다.
        importantHighMarkers.clear();
    }

    public void removeImportantLowMarker(){

        for(Object tag : importantLowMarkers){
            transformImportantNormalMarker(tag);
        }

        importantLowMarkers.clear();
    }
    public void transformImportantHighMarker(@NonNull Object makerTag){
        Marker marker = getMarker(makerTag);
        marker.setZIndex(IMPORTANT_HIGH);
        marker.setForceShowIcon(true);
        showMarker(marker);
        importantHighMarkers.add(makerTag);
    }

    public void transformImportantNormalMarker(@NonNull Object makerTag){
        Marker marker = getMarker(makerTag);
        marker.setZIndex(IMPORTANT_NORMAL);
        marker.setForceShowIcon(true);
        showMarker(marker);
    }

    public void transformImportantLowMarker(@NonNull Object makerTag){
        Marker marker = getMarker(makerTag);
        marker.setZIndex(IMPORTANT_LOW);
        marker.setForceShowIcon(false);
        importantLowMarkers.add(makerTag);
    }

    public Marker getMarker(@NonNull Object makerTag){
        return markerMap.get(makerTag);
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
