package com.example.bwtools.android.tools.mvp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bwtools.R;
import com.example.bwtools.android.tools.mvp.network.BaseRetrofitInterface;
import com.example.bwtools.android.tools.imp.PageManager;
import com.example.bwtools.android.util.CommonUtils;

import java.util.ArrayList;

import retrofit2.Response;


public abstract class BaseRetrofitNetwork<OnPost,CallType extends BaseJson> extends AsyncTask<Void,Integer, ArrayList<OnPost>> implements BaseRetrofitInterface<ArrayList<OnPost>,CallType> {
    public String TAG = "BaseRetrofitPageNetwork";

    protected final String RESPONSE_SUCCESS = "200";
    protected final String CLIENT_REQUEST_VALUE_ERROR = "400";
    protected final String CLIENT_REQUEST_ERROR = "404";
    protected final String SERVER_ERROR = "500";
    protected final String SERVER_DB_QUERY_ERROR = "1000";
    protected final String SERVER_GPS_ERROR = "1100";
    protected final String SERVER_AUTH_EMAIL_ERROR = "1200";
    protected final int PROGRESS_MAX = 100;

    private ProgressDialog progressDialog;
    protected Context context;
    private PageManager pageManager;

    protected int prgressStatus;
    private boolean isExecute;
    private boolean isResponseSuccess;
    protected boolean isUseLoadingBar = true;


    public BaseRetrofitNetwork(Context context) {
        this.context = context;
        isExecute = false;
        isResponseSuccess = true;
    }

    public BaseRetrofitNetwork(Context context, PageManager pageManager) {
        this.context = context;
        this.pageManager =pageManager;
        isExecute = false;
        isResponseSuccess = true;
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
        if(isUseLoadingBar){
            setPrgressStatus(values[0]);
        }
    }

    @Override
    protected ArrayList<OnPost> doInBackground(Void ... voids) {
        Response<CallType> response = getResponseAfterCall();

        //404 Error
        if(response == null){
            Log.e(TAG,"Check Request Call! #Reason : Response is NULL");
            return new ArrayList<>();

        }

        if(response.isSuccessful()){
            String code = response.body().getCode() != null ? response.body().getCode() : CLIENT_REQUEST_ERROR;

            if(code.equals(RESPONSE_SUCCESS)){
                return handleResponse(response);
            }
            else
                handleError(response.body().getErrorMessage());
        }

        return new ArrayList<>();
    }

    abstract public Response<CallType> getResponseAfterCall();


    @Override
    protected void onPostExecute( ArrayList<OnPost> onPost) {
        super.onPostExecute(onPost);
        OnPostExcute(onPost);
        if(isUseLoadingBar)
            cancelProgressDialog();
        isExecute=false;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(isUseLoadingBar)
            cancelProgressDialog();
        isExecute=false;
        isResponseSuccess = false;
    }



    public void cancelProgressDialog(){
        if(isUseLoadingBar){
            progressDialog.setProgress(PROGRESS_MAX);
            progressDialog.dismiss();
        }
    }

    @Override
    public void handleError(String ErrorCode) {
        isResponseSuccess = false;
        switch (ErrorCode){
            case CLIENT_REQUEST_ERROR:
                Log.e(TAG, "Response Error! #Code : CLIENT_REQUEST_ERROR");
                break;

            case CLIENT_REQUEST_VALUE_ERROR:
                Log.e(TAG, "Response Error! #Code : CLIENT_REQUEST_VALUE_ERROR");
                break;

            case SERVER_AUTH_EMAIL_ERROR:
                Log.e(TAG, "Response Error! #Code : SERVER_AUTH_EMAIL_ERROR");
                break;

            case SERVER_ERROR:
                Log.e(TAG, "Response Error! #Code : SERVER_ERROR");
                break;

            case SERVER_DB_QUERY_ERROR:
                Log.e(TAG, "Response Error! #Code : SERVER_DB_QUERY_ERROR");
                break;

            case SERVER_GPS_ERROR:
                Log.e(TAG, "Response Error! #Code : SERVER_GPS_ERROR");
                break;
        }
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


    public void setPrgressStatus(int value) {
        this.prgressStatus = value;
        this.progressDialog.setProgress(value);
    }

}
