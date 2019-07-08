package com.example.bwtools.android.tools.mvp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.bwtools.R;
import com.example.bwtools.android.tools.mvp.network.BaseApiServerInterface;
import com.example.bwtools.android.tools.imp.PageManager;
import com.example.bwtools.android.util.CommonUtils;

import java.util.ArrayList;

public abstract class BaseNetwork<doInBackground,OnPost> extends AsyncTask<doInBackground,Integer, ArrayList<OnPost>> implements BaseApiServerInterface<ArrayList<OnPost>> {
    private final int PROGRESS_MAX = 100;

    private ProgressDialog progressDialog;
    protected Context context;
    private PageManager pageManager;
    private boolean isExecute;
    private boolean isResponseSuccess;

    private int prgressStatus;
    private boolean isUseLoadingBar = true;

    protected BaseNetwork(Context context) {
        this.context = context;
        isExecute = false;
        isResponseSuccess = true;
    }

    protected BaseNetwork(Context context, PageManager pageManager) {
        this.context = context;
        this.pageManager = pageManager;
        isExecute = false;
        isResponseSuccess = true;
    }

    private void setPrgressStatus(int value) {
        this.prgressStatus = value;
        this.progressDialog.setProgress(value);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        isExecute = true;
        if(isUseLoadingBar){
            progressDialog = CommonUtils.showLoadingDialog(context, R.layout.progress_dialog);
            progressDialog.setMax(PROGRESS_MAX);
            setPrgressStatus(0);
        }

        OnPreExcute();
    }

    @Override
    protected void onProgressUpdate(Integer ... values) {
        super.onProgressUpdate(values);
        if(isUseLoadingBar)
            setPrgressStatus(values[0]);
    }

    @Override
    protected void onPostExecute( ArrayList<OnPost> onPost) {
        super.onPostExecute(onPost);
        if(onPost == null)
            isResponseSuccess = false;

        if(isResponseSuccess())
            OnPostExcute(onPost);

        if(isUseLoadingBar)
            cancelProgressDialog();
        isExecute=false;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(isUseLoadingBar){
            cancelProgressDialog();
            isExecute=false;
            isResponseSuccess = false;
        }

    }

    private void cancelProgressDialog(){
        progressDialog.setProgress(PROGRESS_MAX);
        progressDialog.dismiss();
    }

    @Override
    @Deprecated
    public ArrayList<OnPost> handleResponse() {
        return null;
    }

    @Override
    @Deprecated
    public void handleError(String ErrorCode) {

    }

    protected boolean isNull(Object o){
        return o == null ? true : false;
    }

    protected abstract void OnPostExcute( ArrayList<OnPost> data);
    protected abstract void OnPreExcute();


    public boolean isExecute() {
        return isExecute;
    }

    public boolean isResponseSuccess() {
        return isResponseSuccess;
    }

    protected void setPageManagerPageEnd(boolean isPageEnd) {
        pageManager.setPageEnd(isPageEnd);
    }

    protected void setPageManagerPageNow(int pageNow) {
        pageManager.setPageNow(pageNow);
    }

    public void setPageManagerPageTotal(int pageTotal) {
        pageManager.setPageTotal(pageTotal);
    }

    public boolean isPageManagerPageEnd() {
        return pageManager.isPageEnd();
    }

    protected int getPageManagerPageNow() {
        return pageManager.getPageNow();
    }

    public int getPageManagerPageTotal() {
        return pageManager.getPageTotal();
    }

    public void resetPageManagerPage(){
        pageManager.resetPage();
    }
}
