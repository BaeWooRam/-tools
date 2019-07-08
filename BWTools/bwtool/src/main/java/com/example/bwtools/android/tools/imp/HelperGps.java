package com.example.bwtools.android.tools.imp;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.core.content.ContextCompat;


public class HelperGps extends Service implements LocationListener {
    private final Context mContext;
    private final int GEOGODE_RESULT_MAX = 20;

    private Geocoder geocoder;
    // 현재 GPS 사용유무
    boolean isGPSEnabled = false;

    // 네트워크 사용유무
    boolean isNetworkEnabled = false;

    // GPS 상태값
    boolean isGetLocation = false;

    Location location;
    double lat; // 위도
    double lon; // 경도

    // 최소 GPS 정보 업데이트 거리 10미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    // 최소 GPS 정보 업데이트 시간 밀리세컨이므로 1분
    private static final long MIN_TIME_BW_UPDATES = 1000;

    protected LocationManager locationManager;

    public HelperGps(Context context) {
        this.mContext = context;
        this.geocoder = new Geocoder(context, Locale.KOREAN);
    }

    public HelperGps(Context context, Geocoder geocoder) {
        this.mContext = context;
        this.geocoder = geocoder;
    }

    public Location getLocation() {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(
                        mContext, android.Manifest.permission.ACCESS_FINE_LOCATION )
                        != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return null;
        }

        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // GPS 정보 가져오기
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // 현재 네트워크 상태 값 알아오기
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                showSettingsAlert();
            } else {
                this.isGetLocation = true;
                // 네트워크 정보로 부터 위치값 가져오기
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            // 위도 경도 저장
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }

                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    /**
     * GPS 종료
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(HelperGps.this);
        }
    }

    /**
     * 위도값을 가져옵니다.
     * */
    public double getLatitude(){
        if(location != null){
            lat = location.getLatitude();
        }
        return lat;
    }

    /**
     * 경도값을 가져옵니다.
     * */
    public double getLongitude(){
        if(location != null){
            lon = location.getLongitude();
        }
        return lon;
    }

    /**
     * GPS 나 wife 정보가 켜져있는지 확인합니다.
     * */
    public boolean isGetLocation() {
        return this.isGetLocation;
    }

    /**
     * GPS 정보를 가져오지 못했을때
     * 설정값으로 갈지 물어보는 alert 창
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS 사용유무 설정");
        alertDialog.setMessage("현재 GPS 설정이 되지 않았습니다. \n 설정창으로 가시겠습니까?");

        // OK 를 누르게 되면 설정창으로 이동합니다.
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });
        // Cancle 하면 종료 합니다.
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onLocationChanged(Location location) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {

    }

    public String getTransformMyLocationToAddress(Location currentLocation) throws IOException {
        try {
            List<Address> fromLocation = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), GEOGODE_RESULT_MAX);
            String currentAddress = fromLocation.get(0).getAddressLine(0).replace("대한민국 ","");

            for(Address address:fromLocation){
                Log.e("Address",""+address.getAddressLine(0).replace("대한민국 ",""));
                Log.e("AdminArea",""+address.getAdminArea());
                Log.e("CountryName",""+address.getCountryName());
                Log.e("Locality",""+address.getLocality());
                Log.e("SubLocality",""+address.getSubLocality());
            }

            return currentAddress;
        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }
    }

    public Location findGeoPoint(String targetAddress) {
        Location loc = new Location("");
        List<Address> addr = null;// 한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 설정

        try {
            addr = geocoder.getFromLocationName(targetAddress, GEOGODE_RESULT_MAX);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addr != null) {
            for (Address address :addr) {
                Log.i("helperGps","location longitude = "+address.getLongitude()+", location latitude"+address.getLatitude());
            }
            loc.setLatitude(addr.get(0).getLatitude());
            loc.setLongitude(addr.get(0).getLongitude());
        }

        return loc;
    }

}

