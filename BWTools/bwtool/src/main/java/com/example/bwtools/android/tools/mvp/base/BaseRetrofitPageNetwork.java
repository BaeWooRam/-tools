package com.example.bwtools.android.tools.mvp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bwtools.R;
import com.example.bwtools.android.tools.mvp.network.BaseRetrofitPageInterface;
import com.example.bwtools.android.tools.imp.PageManager;
import com.example.bwtools.android.util.CommonUtils;

import java.util.ArrayList;

import retrofit2.Response;


public abstract class BaseRetrofitPageNetwork<OnPost,CallType extends BaseJsonPage> extends AsyncTask<Void,Integer, ArrayList<OnPost>> implements BaseRetrofitPageInterface<ArrayList<OnPost>,CallType> {
    private String TAG = "BaseRetrofitPageNetwork";

    private final String RESPONSE_SUCCESS = "200";
    private final String CLIENT_REQUEST_VALUE_ERROR = "400";
    private final String CLIENT_REQUEST_ERROR = "404";
    private final String SERVER_ERROR = "500";
    private final String SERVER_DB_QUERY_ERROR = "1000";
    private final String SERVER_GPS_ERROR = "1100";
    private final String SERVER_AUTH_EMAIL_ERROR = "1200";

    private final int PROGRESS_MAX = 100;

    private ProgressDialog progressDialog;
    private Context context;
    private PageManager pageManager;

    private int prgressStatus;
    private boolean isExecute;
    private boolean isResponseSuccess;

    public BaseRetrofitPageNetwork(Context context) {
        this.context = context;
        isExecute = false;
        isResponseSuccess = true;
    }

    protected BaseRetrofitPageNetwork(Context context, PageManager pageManager) {
        this.context = context;
        this.pageManager =pageManager;
        isExecute = false;
        isResponseSuccess = true;
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
        setPrgressStatus(values[0]);
    }

    @Override
    protected ArrayList<OnPost> doInBackground(Void... voids) {
        Response<CallType> response = getResponseAfterCall();

        if(response == null){
            handleError("서버와 연결할 수 없습니다.");
            isResponseSuccess = false;
            return new ArrayList<>();
        }

        if(response.isSuccessful()){
            String code = response.body().getCode() != null ? response.body().getCode() : CLIENT_REQUEST_ERROR;

            if(code.equals(RESPONSE_SUCCESS)){
                int rowCurrent = response.body().getPageNow();
                int rowTotal = response.body().getPageTotal();

                if(pageManager != null){
                    pageManager.setPageNow(rowCurrent+1);
                    pageManager.setPageTotal(rowTotal);

                    if(rowCurrent==rowTotal){
                        handlePageEnd();
                    }
                }

                return handleResponse(response);
            }
            else
                handleError(response.body().getErrorMessage());
        }

        return new ArrayList<>();
    }

    abstract protected Response<CallType> getResponseAfterCall();

    protected void handlePageEnd(){
        pageManager.setPageEnd(true);
    }

    @Override
    protected void onPostExecute( ArrayList<OnPost> onPost) {
        super.onPostExecute(onPost);
        if(isResponseSuccess()){
            OnPostExcute(onPost);
        }
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



    private void cancelProgressDialog(){
        progressDialog.setProgress(PROGRESS_MAX);
        progressDialog.dismiss();
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

    protected int getPageManagerPageNow() {
        return pageManager.getPageNow();
    }

    public int getPageManagerPageTotal() {
        return pageManager.getPageTotal();
    }

    protected void resetPageManagerPage(){
        pageManager.resetPage();
    }

    private void setPrgressStatus(int value) {
        this.prgressStatus = value;
        this.progressDialog.setProgress(value);
    }

}
