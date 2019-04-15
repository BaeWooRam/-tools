package com.example.bwtools.android.tools.interfaces;

import java.util.List;

public interface AppPermissionIlmp {
    List<String> getFragmentGrantedPermissionList();
    List<String> getActivityGrantedPermissionList();

    String[] getRequiredFragmentPermissions();
    String[] getRequiredActivityPermissions();
    void checkAndRequestFragmentPermission();
    void checkAndRequestActivtiyPermission();
    void permitFragment();
    void permitActivtiy();
}
