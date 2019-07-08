package com.example.bwtools.android.tools.mvp.base;

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
import com.example.bwtools.android.tools.mvp.MvpFragment;
import com.example.bwtools.android.tools.mvp.MvpView;
import com.example.bwtools.android.util.CommonUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<Presenter> extends Fragment implements MvpFragment, MvpView<Presenter> {
    private ProgressDialog mProgressDialog;
    protected BaseActivity parentActivity;
    protected Presenter presenter;
    public String TAG;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentActivity = (BaseActivity) getActivity();
        TAG = getClass().getSimpleName();
        return inflater.inflate(getLayoutId(), container, false);
    }


    @LayoutRes
    public abstract int getLayoutId();

    @Override
    public void setupPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(parentActivity, R.layout.progress_dialog);
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(parentActivity, "메시지 내용이 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void showDialog(String message) {
        new AlertDialog.Builder(parentActivity)
                .setMessage(message)
                .setPositiveButton("확인",null)
                .show();
    }


    @Override
    public void showDialog(String message, DialogInterface.OnClickListener NegativeClickHandler, DialogInterface.OnClickListener PositiveClickHandler) {
        new AlertDialog.Builder(parentActivity)
                .setMessage(message)
                .setNegativeButton("취소", NegativeClickHandler)
                .setPositiveButton("확인", PositiveClickHandler)
                .show();
    }

    @Override
    public void showDialog(String message, DialogInterface.OnClickListener PositiveClickHandler) {
        new AlertDialog.Builder(parentActivity)
                .setMessage(message)
                .setPositiveButton("확인", PositiveClickHandler)
                .show();
    }

    @Override
    public void killApp() {
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.finishAffinity(parentActivity);
                System.runFinalization();
                System.exit(0);
            }
        };
        showDialog((String)getText(R.string.network_error), clickListener);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void hideKeyboard() {
        View view = parentActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    parentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void setUpActivtiyToolbar(View view) {
        parentActivity.setUpCustomToolbar(view);
    }

    @Override
    public void setUpActivtiyToolbar(int layoutID) {
        parentActivity.setUpCustomToolbar(layoutID);
    }

    @Override
    public void setUpFragmentToolbar(View view, Toolbar targetToolbar) {
        parentActivity.setUpCustomToolbar(view, targetToolbar);
    }

    @Override
    public void setUpFragmentToolbar(int layoutID, Toolbar targetToolbar) {
        parentActivity.setUpCustomToolbar(layoutID, targetToolbar);
    }

}
