package com.example.bwtools.android.tools.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bwtools.R;
import com.example.bwtools.android.tools.base.mvp.MvpActivity;
import com.example.bwtools.android.tools.base.mvp.MvpView;
import com.example.bwtools.android.util.CommonUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public abstract class BaseActivity<Presenter> extends AppCompatActivity implements MvpActivity, MvpView<Presenter> {
    private ProgressDialog mProgressDialog;
    protected Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setupActivityResource());
        SetUp();
    }

    @LayoutRes
    public abstract int setupActivityResource();

    public abstract void SetUp();

    @Override
    public void setupPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setUpToolbar(int layoutID, String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View CustomView = View.inflate(getApplicationContext(),layoutID,null);
        actionBar.setCustomView(CustomView,new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT
        ));
        ((TextView)CustomView.findViewById(R.id.title)).setText(title);
        Toolbar perent = (Toolbar) CustomView.getParent();
        perent.setContentInsetsAbsolute(0,0);
    }

    @Override
    public void setUpToolbar(View view) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        actionBar.setCustomView(view,new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT
        ));

        Toolbar perent = (Toolbar) view.getParent();
        perent.setContentInsetsAbsolute(0,0);

    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this, R.layout.progress_dialog);
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "메시지 내용이 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void showDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setNegativeButton("취소",null)
                .setPositiveButton("확인",null)
                .show();
    }

    @Override
    public void showDialog(String message, DialogInterface.OnClickListener NegativeClickHandler, DialogInterface.OnClickListener PositiveClickHandler) {
        new AlertDialog.Builder(this)
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
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
