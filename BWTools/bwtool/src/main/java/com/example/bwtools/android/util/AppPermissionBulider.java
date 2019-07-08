package com.example.bwtools.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class AppPermissionBulider {
    public static final int CHECK_PERMISSION = 100;
    private String[] mRequestPermission;
    private Activity mActContext;
    private Fragment mFrgContext;

    public AppPermissionBulider setmRequestPermission(String... mRequestPermission) {
        this.mRequestPermission = mRequestPermission;
        return this;
    }

    public AppPermissionBulider setActivityContext(Activity mActContext) {
        this.mActContext = mActContext;
        return this;
    }

    public AppPermissionBulider setFragmentContext(Fragment mFrgContext) {
        this.mFrgContext = mFrgContext;
        return this;
    }

    public AppPermission build() {
        Log.e("AppPermissionBulider", "build() start!");
        if(mActContext != null){
            Log.e("AppPermissionBulider", "Activity AppPermission!");
            return new AppPermission(mActContext,mRequestPermission);
        }
        else{
            if(mFrgContext != null){
                Log.e("AppPermissionBulider", "Fragment AppPermission!");
                return new AppPermission(mFrgContext,mRequestPermission);
            }
            else
                throw new Error("AppPermissionBulider build() Error : Please Input Activtiy Context or Fragmet Context");
        }

    }

    class AppPermission{

        public AppPermission(Activity context, String[] requestPermission) {
            if(requestPermission != null && requestPermission.length>0){
                Log.e("AppPermission", "Start!");
                checkAndRequestPermission(context, AppPermissionBulider.CHECK_PERMISSION,requestPermission);
            }
            else
                throw new Error("AppPermissionBulider build() Error : Please Input Permission!");
        }

        public AppPermission(Fragment context, String[] requestPermission) {
            if(requestPermission != null && requestPermission.length>0){
                Log.e("AppPermission", "Start!");
                checkAndRequestPermission(context, AppPermissionBulider.CHECK_PERMISSION,requestPermission);
            }
            else
                throw new Error("AppPermissionBulider build() Error : Please Input Permission!");

        }




        public boolean checkAndRequestPermission(Activity activity, int permissionRequestCode, String... permissions) {
            String[] requiredPermissions = getRequiredPermissions(activity, permissions);
            if ((requiredPermissions.length > 0) && (!activity.isDestroyed())) {
                ActivityCompat.requestPermissions(activity, requiredPermissions, permissionRequestCode);
                return false;
            } else {
                return true;
            }
        }



        public boolean checkAndRequestPermission(Fragment fragment, int permissionRequestCode, String... permissions) {
            String[] requiredPermissions = getRequiredPermissions(fragment.getContext() != null ?
                    fragment.getContext() : fragment.getActivity(), permissions);

            if (requiredPermissions.length > 0 && fragment.isAdded()) {
                fragment.requestPermissions(requiredPermissions, permissionRequestCode);
                return false;
            } else {
                return true;
            }
        }

        public String[] getRequiredPermissions(Context context, String... permissions) {
            List<String> requiredPermissions = new ArrayList<>();

            // Context가 null이면 무조건 권한을 요청하도록 requiredPermissions가 존재한다고 reutrn 한다
            if (context == null) return requiredPermissions.toArray(new String[1]);

            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    requiredPermissions.add(permission);
                }
            }

            return requiredPermissions.toArray(new String[requiredPermissions.size()]);
        }


        public boolean verifyPermissions(int[] grantResults) {
            // At least one result must be checked.
            if (grantResults.length < 1) return false;

            // Verify that each required permission has been granted, otherwise return false.
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) return false;
            }
            return true;
        }
    }

}