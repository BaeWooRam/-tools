package com.example.bwtools.android.tools.imp;

import android.app.Activity;
import android.content.pm.PackageManager;


import com.example.bwtools.android.tools.interfaces.AppPermissionIlmp;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class FactoryAppPermission implements AppPermissionIlmp {
    public final int CHECK_PERMISSION = 100;
    private final String TAG = "BuliderAppPermission";
    private final String[] RequestPermission;
    private Activity targetActivity;
    private Fragment targetFragment;

    public FactoryAppPermission(String[] RequestPermission, Activity thisActivity) {
        this.RequestPermission = RequestPermission;
        this.targetActivity = thisActivity;
    }

    public FactoryAppPermission(String[] RequestPermission, Fragment thisFragment) {
        this.RequestPermission = RequestPermission;
        this.targetFragment = thisFragment;
    }

    @Override
    public void permitActivtiy() {
        checkRequestPermission();
        checkAndRequestActivtiyPermission();
    }

    @Override
    public void checkAndRequestActivtiyPermission() {
        String[] requiredPermissions = getRequiredActivityPermissions();

        if (isEmptyRequestPermission(requiredPermissions) && (!targetActivity.isDestroyed())) {
            ActivityCompat.requestPermissions(targetActivity, requiredPermissions, CHECK_PERMISSION);
        } else
            new Error(TAG+" Empty requirePermission or Activity is Destroyed");
    }


    @Override
    public String[] getRequiredActivityPermissions() {
        List<String> requiredPermissions = getActivityGrantedPermissionList();
        return requiredPermissions.toArray(new String[requiredPermissions.size()]);
    }

    @Override
    public List<String> getActivityGrantedPermissionList() {
        List<String> requiredPermissions = new ArrayList<>();

        for (String permission : RequestPermission) {
            if (ContextCompat.checkSelfPermission(targetActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                requiredPermissions.add(permission);
            }
        }
        return requiredPermissions;
    }

    @Override
    public void permitFragment() {
        checkRequestPermission();
        checkAndRequestFragmentPermission();
    }


    @Override
    public void checkAndRequestFragmentPermission() {
        String[] requiredPermissions = getRequiredFragmentPermissions();

        if (isEmptyRequestPermission(requiredPermissions) && targetFragment.isAdded()) {
            targetFragment.requestPermissions(requiredPermissions, CHECK_PERMISSION);
        } else
            new Error(TAG+" Empty requirePermission or Activity is Destroyed");
    }

    @Override
    public String[] getRequiredFragmentPermissions() {
        List<String> requiredPermissions = getFragmentGrantedPermissionList();
        return requiredPermissions.toArray(new String[requiredPermissions.size()]);
    }

    @Override
    public List<String> getFragmentGrantedPermissionList() {
        List<String> requiredPermissions = new ArrayList<>();

        for (String permission : RequestPermission) {
            if (ContextCompat.checkSelfPermission(targetFragment.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                requiredPermissions.add(permission);
            }
        }
        return requiredPermissions;
    }



    private void checkRequestPermission(){
        if(RequestPermission == null && RequestPermission.length==0)
            new Error(TAG+"Error! RequestPermission is Null or not input RequestPermission");
    }

    private boolean isEmptyRequestPermission(String[] RequestPermission){
        if(RequestPermission != null && RequestPermission.length>0)
            return true;
        else
            return false;
    }

    public boolean isRequestCode(int requestCode) {
        if (requestCode == CHECK_PERMISSION)
            return true;
        else
            return false;
    }

    public boolean isGrantResults(int[] grantResults) {
        if (grantResults.length > 0)
            return true;
        else
            return false;
    }

    public int getGrantResultsCount(int[] grantResults) {
        int count=0;
        for (int p : grantResults){
            if(p == PackageManager.PERMISSION_DENIED)
                count++;
        }
        return count;
    }

    public int getActivtiyGrantCount(){
        int count=0;
        for(String permission : RequestPermission){
            count += ContextCompat.checkSelfPermission(targetActivity,permission);
        }
        return count;
    }

    public int getFragmentGrantCount(){
        int count=0;
        for(String permission : RequestPermission){
            count += ContextCompat.checkSelfPermission(targetFragment.getContext(),permission);
        }
        return count;
    }

}