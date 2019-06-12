package com.example.bwtools.android.tools.base.mvp;

import android.view.View;

import androidx.appcompat.widget.Toolbar;


public interface MvpFragment extends BaseView {
    void setUpActivtiyToolbar(View customView);
    void setUpActivtiyToolbar(int layoutID);
    void setUpFragmentToolbar(View customView, Toolbar targetToolbar);
    void setUpFragmentToolbar(int layoutID, Toolbar targetToolbar);
}
