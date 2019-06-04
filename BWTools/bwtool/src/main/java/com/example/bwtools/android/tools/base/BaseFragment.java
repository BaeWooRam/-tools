package com.example.bwtools.android.tools.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.bwtools.R;
import com.example.bwtools.android.tools.base.mvp.BaseView;
import com.example.bwtools.android.util.CommonUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment implements BaseView {
    private ProgressDialog mProgressDialog;
    private View thisLayout;
    private Activity thisActivity;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisLayout = inflater.inflate(getLayoutId(), container, false);
        thisActivity = getActivity();
        SetUp();
        return thisLayout;
    }

    @LayoutRes
    public abstract int getLayoutId();

    public View getFragmentLayout(){
        return thisLayout;
    }

    public abstract void SetUp();

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(thisActivity, R.layout.progress_dialog);
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(thisActivity, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(thisActivity, getString(R.string.string_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void showDialog(String message) {
        new AlertDialog.Builder(thisActivity)
                .setMessage(message)
                .setNegativeButton("취소",null)
                .setPositiveButton("확인",null)
                .show();
    }

    @Override
    public void showDialog(String message, DialogInterface.OnClickListener NegativeClickHandler, DialogInterface.OnClickListener PositiveClickHandler) {
        new AlertDialog.Builder(thisActivity)
                .setMessage(message)
                .setNegativeButton("취소", NegativeClickHandler)
                .setPositiveButton("확인", PositiveClickHandler)
                .show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void hideKeyboard() {
        View view = thisActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    thisActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
