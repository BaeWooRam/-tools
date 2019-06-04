package com.example.bwtools.android.view.imp;

import android.app.Activity;
import android.view.MenuItem;

import com.example.bwtools.android.view.interfaces.BottomNavigationImp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public abstract class FactoryBottomNavigation implements BottomNavigationImp {
    private final String TAG = "FactoryBottomNavigation";
    private BottomNavigationView bottomNavigation;
    private Activity targetActivity;

    private FactoryBottomNavigation() { }

    public FactoryBottomNavigation(Activity thisActivity) {
        this.targetActivity = thisActivity;
    }

    @Override
    public void setupBottomNavigationAndListener(int view) {
        bottomNavigation = targetActivity.findViewById(view);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void setupItemBackgroundResource(int color) {
        bottomNavigation.setItemBackgroundResource(color);
    }

    @Override
    public void setupBackgroundResource(int color) {
        bottomNavigation.setBackgroundResource(color);
    }

    @Override
    public void setupSelectedItem(int SelectedItemID) {
        bottomNavigation.setSelectedItemId(SelectedItemID);
    }
    /**
     *  생성자에서 초기화시키고 따로 사용하지 않는 거를 추천합니다.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return setupNavigationSelectedHandler(menuItem);
    }

    @Override
    public void setupItemTextColorResource(int color) {
        bottomNavigation.setItemTextColor(ContextCompat.getColorStateList(targetActivity.getApplicationContext(), color));
    }

    @Override
    public void setupItemIconColorResource(int color) {
        bottomNavigation.setItemIconTintList(ContextCompat.getColorStateList(targetActivity.getApplicationContext(), color));
    }

}
