package com.example.bwtools.android.tools.imp;

import com.example.bwtools.android.tools.dto.RequestBody;
import com.example.bwtools.android.tools.dto.RequestHead;
import com.example.bwtools.android.tools.dto.RequestQuery;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ApiRequest {
    public final String HttpCONNECTTION_GET = "GET";
    public final String HttpCONNECTTION_POST = "POST";
    private String requestMethod;
    private String BaseURL;
    private String responseResult;
    private ArrayList<RequestQuery> requestQueryList;
    private ArrayList<RequestBody> requestBodyList;
    private ArrayList<RequestHead> requestHeaderList;
    private boolean doAutoClear=true;
    private boolean isDebug = true;

    public ApiRequest() {
        requestQueryList = new ArrayList();
        requestBodyList = new ArrayList();
        requestHeaderList = new ArrayList();
    }

    public void setupRequestInfo(String BaseURL, String requestMethod){
        this.BaseURL = BaseURL;
        this.requestMethod = requestMethod;
    }

    public void setupDoAutoClearAfterRequest(boolean doAutoClear) {
        this.doAutoClear = doAutoClear;
    }

    public void addRequestQuery(RequestQuery requestQuery) {
        this.requestQueryList.add(requestQuery);
    }

    public void addRequestBody(RequestBody requestBody) {
        this.requestBodyList.add(requestBody);
    }

    public void addRequestHead(RequestHead requestHead) {
        this.requestHeaderList.add(requestHead);
    }

    private void clearRequestBody() {
        this.requestBodyList.clear();
    }

    private void clearRequestHead() {
        this.requestHeaderList.clear();
    }

    private void clearRequestQuery() {
        this.requestQueryList.clear();
    }

    public void startRequest() {
        try {
            String requestURL = getRequestURL();
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "utf-8");
            con.setRequestMethod(this.requestMethod);

            if(isDebug)
                System.out.println(con.toString());

            this.settingRequestHead(con);

            int responseCode = con.getResponseCode();

            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            StringBuffer response = new StringBuffer();

            String inputLine;
            while((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            br.close();

            if(isDebug)
                System.out.println("response = "+response.toString());

            setResponseResult(response.toString());
        } catch (Exception e) {
            System.out.println("startRequest Error! "+e.toString());
        }

        if(doAutoClear){
            clearRequestBody();
            clearRequestHead();
            clearRequestQuery();
        }
    }

    private void setResponseResult(String responseResult) {
        this.responseResult = responseResult != null ? responseResult : "결과 없음";
    }

    public String getResponseResult() {
        return this.responseResult;
    }


    private String getRequestURL() {
        StringBuilder request = new StringBuilder();

        for(int index=0;index<requestQueryList.size();index++) {
            RequestQuery requestQuery = requestQueryList.get(index);
            String propertyKey = requestQuery.getKey();
            String propertyValue = requestQuery.getQuery();

            if(index==0){
                request.append(BaseURL);
                request.append("?"+propertyKey);
                request.append("="+propertyValue);
            }
            else{
                request.append("&"+propertyKey);
                request.append("="+propertyValue);
            }
        }

        return request.toString();
    }

    private void settingRequestHead(HttpURLConnection con) {
        if(requestHeaderList.size()==0)
            return;

        for(RequestHead requestHead :requestHeaderList) {
            String propertyKey = requestHead.getKey();
            String propertyValue = requestHead.getValue();
            con.setRequestProperty(propertyKey, propertyValue);
        }
    }

    @Deprecated
    public void settingRequestBody() {
    }
}
