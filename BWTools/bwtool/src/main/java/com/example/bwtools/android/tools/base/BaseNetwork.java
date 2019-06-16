package com.example.bwtools.android.tools.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.bwtools.R;
import com.example.bwtools.android.tools.base.mvp.network.BaseApiServerInterface;
import com.example.bwtools.android.tools.imp.PageManager;
import com.example.bwtools.android.util.CommonUtils;

import java.util.ArrayList;

public abstract class BaseNetwork<doInBackground,OnPost> extends AsyncTask<doInBackground,Integer, ArrayList<OnPost>> implements BaseApiServerInterface<ArrayList<OnPost>> {
    protected final int PROGRESS_MAX = 100;

    private ProgressDialog progressDialog;
    protected Context context;
    private PageManager pageManager;
    private boolean isExecute;
    private boolean isResponseSuccess;

    protected int prgressStatus;

    public BaseNetwork(Context context) {
        this.context = context;
        isExecute = false;
        isResponseSuccess = true;
    }

    public BaseNetwork(Context context, PageManager pageManager) {
        this.context = context;
        this.pageManager = pageManager;
        isExecute = false;
        isResponseSuccess = true;
    }

    public void setPrgressStatus(int value) {
        this.prgressStatus = value;
        this.progressDialog.setProgress(value);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        isExecute = true;
        progressDialog = CommonUtils.showLoadingDialog(context, R.layout.progress_dialog);
        progressDialog.setMax(PROGRESS_MAX);
        setPrgressStatus(0);
        OnPreExcute();
    }

    @Override
    protected void onProgressUpdate(Integer ... values) {
        super.onProgressUpdate(values);
        setPrgressStatus(values[0].intValue());
    }

    @Override
    protected void onPostExecute( ArrayList<OnPost> onPost) {
        super.onPostExecute(onPost);
        OnPostExcute(onPost);
        cancelProgressDialog();
        isExecute=false;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        cancelProgressDialog();
        isExecute=false;
        isResponseSuccess = false;
    }


    public void cancelProgressDialog(){
        progressDialog.setProgress(PROGRESS_MAX);
        progressDialog.dismiss();
    }


    public boolean isNull(Object o){
        return o == null ? true : false;
    }

    public abstract void OnPostExcute( ArrayList<OnPost> data);
    public abstract void OnPreExcute();


    public boolean isExecute() {
        return isExecute;
    }

    public boolean isResponseSuccess() {
        return isResponseSuccess;
    }

    public void setPageManagerPageEnd(boolean isPageEnd) {
        pageManager.setPageEnd(isPageEnd);
    }

    public void setPageManagerPageNow(int pageNow) {
        pageManager.setPageNow(pageNow);
    }

    public void setPageManagerPageTotal(int pageTotal) {
        pageManager.setPageTotal(pageTotal);
    }

    public boolean isPageManagerPageEnd() {
        return pageManager.isPageEnd();
    }

    public int getPageManagerPageNow() {
        return pageManager.getPageNow();
    }

    public int getPageManagerPageTotal() {
        return pageManager.getPageTotal();
    }

    public void resetPageManagerPage(){
        pageManager.resetPage();
    }
}
