package com.example.bwtools.android.tools.base.mvp;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

public interface MvpActivity extends BaseView {
    void setUpCustomToolbar(View CustomView);
    void setUpCustomToolbar(View CustomView, Toolbar toolbar);
    void setUpCustomToolbar(int layoutID);
    void setUpCustomToolbar(int layoutID, Toolbar toolbar);
}
