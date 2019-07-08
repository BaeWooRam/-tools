package com.example.bwtools.android.tools.mvp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.bwtools.R;
import com.example.bwtools.android.tools.mvp.MvpActivity;
import com.example.bwtools.android.tools.mvp.MvpView;
import com.example.bwtools.android.util.CommonUtils;
import com.example.bwtools.android.util.NetworkUtils;

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
    protected String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!NetworkUtils.isNetworkConnected(getApplicationContext()))
            killApp();

        setContentView(setupActivityResource());
        TAG = getClass().getSimpleName();
        SetUp();
    }

    @LayoutRes
    protected abstract int setupActivityResource();

    protected abstract void SetUp();

    @Override
    public void setupPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setUpCustomToolbar(View CustomView) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

            actionBar.setCustomView(CustomView,new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT
            ));

            actionBar.setTitle("");
            Toolbar perent = (Toolbar) CustomView.getParent();
            perent.setContentInsetsAbsolute(0,0);
        }
    }

    @Override
    public void setUpCustomToolbar(View CustomView, Toolbar toolbar) {
        setSupportActionBar(toolbar);
        setUpCustomToolbar(CustomView);
    }

    public void setUpCustomToolbar(int layoutID, Toolbar toolbar) {
        View CustomView = View.inflate(getApplicationContext(),layoutID,null);
        setSupportActionBar(toolbar);
        setUpCustomToolbar(CustomView);
    }

    @Override
    public void setUpCustomToolbar(int layoutID) {
        View CustomView = View.inflate(getApplicationContext(),layoutID,null);
        setUpCustomToolbar(CustomView);
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
    public void showDialog(String message, DialogInterface.OnClickListener PositiveClickHandler) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("확인", PositiveClickHandler)
                .show();
    }


    @Override
    public void killApp() {
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());
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
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void checkGPS(){
        //GPS가 켜져있는지 체크
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //GPS 설정화면으로 이동
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        }
    }
}
