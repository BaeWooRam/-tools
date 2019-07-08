package com.example.bwtools.android.tools.base.mvp;

import android.content.DialogInterface;

import androidx.annotation.StringRes;

public interface BaseView{
    void showDialog(String message);
    void showDialog(String message, DialogInterface.OnClickListener NegativeClickHandler, DialogInterface.OnClickListener PositiveClickHandler);
    void showMessage(String message);
    void showMessage(@StringRes int resId);
    void hideLoading();
    void showLoading();
}
