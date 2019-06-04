package com.example.bwtools.android.view.interfaces;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

public interface BottomNavigationImp extends BottomNavigationView.OnNavigationItemSelectedListener{
    void setupBottomNavigationAndListener(@IdRes int view);
    void setupItemBackgroundResource(@ColorRes int color);
    void setupBackgroundResource(@ColorRes int color);
    void setupItemTextColorResource(@ColorRes int color);
    void setupItemIconColorResource(@ColorRes int color);
    void setupSelectedItem(@IdRes int SelectedItemID);
    boolean setupNavigationSelectedHandler(@NonNull MenuItem menuItem);
}
