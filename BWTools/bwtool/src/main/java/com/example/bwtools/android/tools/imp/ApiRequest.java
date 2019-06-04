package com.example.bwtools.android.tools.imp;

import com.example.bwtools.android.tools.base.dto.RequestBody;
import com.example.bwtools.android.tools.base.dto.RequestHead;
import com.example.bwtools.android.tools.base.dto.RequestQuery;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ApiRequest {
    public static final String HttpCONNECTTION_GET = "GET";
    public static final String HttpCONNECTTION_POST = "POST";
    private String requestMethod;
    private String BaseURL;
    private String responseResult;
    private ArrayList<RequestQuery> requestQueryList;
    private ArrayList<RequestBody> requestBodyList;
    private ArrayList<RequestHead> requestHeaderList;
    private boolean doAutoClear=true;

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

    public void clearRequestBody() {
        this.requestBodyList.clear();
    }

    public void clearRequestHead() {
        this.requestHeaderList.clear();
    }

    public void clearRequestQuery() {
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
            setResponseResult(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            new Error(e.toString());
        }

        if(doAutoClear){
            clearRequestBody();
            clearRequestHead();
            clearRequestQuery();
        }
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult != null ? responseResult : "결과 없음";
        System.out.println(this.responseResult);
    }

    public String getResponseResult() {
        return this.responseResult;
    }


    public String getRequestURL() {
        String requestURL = BaseURL;

        for(int index=0;index<requestQueryList.size();index++) {
            RequestQuery requestQuery = requestQueryList.get(index);
            String propertyKey = requestQuery.getKey();
            String propertyValue = requestQuery.getQuery();

            if(index==0)
                requestURL += "?"+propertyKey+"="+propertyValue;
            else
                requestURL += "&"+propertyKey+"="+propertyValue;
        }

        return requestURL;
    }

    public void settingRequestHead(HttpURLConnection con) {
        if(requestHeaderList.size()==0)
            return;

        for(RequestHead requestHead :requestHeaderList) {
            String propertyKey = requestHead.getKey();
            Object propertyValue = requestHead.getValue();
            con.setRequestProperty(propertyKey, (String)propertyValue);
        }
    }

    @Deprecated
    public void settingRequestBody() {
    }
}
