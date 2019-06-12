package com.example.bwtools.android.tools.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.bwtools.R;
import com.example.bwtools.android.tools.base.mvp.BaseApiServerInterface;
import com.example.bwtools.android.util.CommonUtils;

import java.util.ArrayList;

public abstract class BaseNetwork<doInBackground,OnPost> extends AsyncTask<doInBackground,Integer, ArrayList<OnPost>> implements BaseApiServerInterface<ArrayList<OnPost>> {
    protected final int PROGRESS_MAX = 100;
    private ProgressDialog progressDialog;
    protected int prgressStatus;
    protected Context context;
    private BaseDataManager<OnPost> baseDataManager;


    public BaseNetwork(Context context) {
        this.context = context;
    }

    public void setPrgressStatus(int value) {
        this.prgressStatus = value;
        this.progressDialog.setProgress(value);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = CommonUtils.showLoadingDialog(context, R.layout.progress_dialog);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
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
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        cancelProgressDialog();
    }

    public BaseDataManager<OnPost> getDataManager(){
        return this.baseDataManager;
    }

    public void setDataManager(BaseDataManager<OnPost> baseDataManager) {
        this.baseDataManager = baseDataManager;
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
}
