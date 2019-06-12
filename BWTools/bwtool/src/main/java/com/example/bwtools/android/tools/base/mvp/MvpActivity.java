package com.example.bwtools.android.tools.base.mvp;

import android.view.View;

public interface MvpActivity extends BaseView{
    void setUpToolbar(View view);
    void setUpToolbar(int layoutID, String title);
}
