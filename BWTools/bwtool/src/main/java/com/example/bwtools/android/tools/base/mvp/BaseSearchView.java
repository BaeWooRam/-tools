package com.example.bwtools.android.tools.base.mvp;

import android.widget.Button;

public interface BaseSearchView extends BaseFilterView {
    void changeFilterButton(Button changeButton, boolean isOn);
    void settingSearchBarText(String message);
}
