package com.example.bwtools.android.tools.imp;

import android.app.ProgressDialog;

import com.example.bwtools.android.tools.dto.RequestHead;
import com.example.bwtools.android.tools.dto.RequestQuery;
import com.example.bwtools.android.tools.interfaces.KaKaOAddressImp;

public class FactoryKaKaOAddress implements KaKaOAddressImp {
    public final String ADDRESS_COL_NAME="address_name";
    public final String ADDRESS_LONGITUDE="y";
    public final String ADDRESS_LATITUDE="x";

    private ApiRequest apiRequest;
    private ProgressDialog progressDialog;


    public FactoryKaKaOAddress(String kakaoApiKey) {
        this.apiRequest = new ApiRequest();
        setupBaseURLAndRequestMethod();
        setupAuthorization(kakaoApiKey);
    }

    @Override
    public void setupBaseURLAndRequestMethod() {
        apiRequest.setupRequestInfo("https://dapi.kakao.com/v2/local/search/address.json", apiRequest.HttpCONNECTTION_GET);
    }

    @Override
    public void setupKeyWord(String query) {
        RequestQuery requestQuery = new RequestQuery("query", query,true);
        apiRequest.addRequestQuery(requestQuery);
    }

    @Override
    public void setupAuthorization(String kakaoApiKey) {
        RequestHead requestHeadAUT = new RequestHead("Authorization", "KakaoAK "+kakaoApiKey);
        apiRequest.addRequestHead(requestHeadAUT);
    }

    @Override
    public void setupAutoClear(boolean doAutoClear) {
        apiRequest.setupDoAutoClearAfterRequest(doAutoClear);
    }

    @Override
    public void startRequestQuery() {
        apiRequest.startRequest();
    }


    @Override
    public String getResponse(){
        return apiRequest.getResponseResult();
    }

    /**
     * @param pageNum 결과 페이지 번호(기본 1)
     * @param displayCount 한 페이지에 보여질 문서의 개수(1-30 사이)
     */
    @Override
    public void setupRequestOption(int pageNum, int displayCount) {
        RequestQuery arrayOption[] = {new RequestQuery("page", Integer.toString(pageNum)),
                new RequestQuery("size",Integer.toString(displayCount))};

        for(RequestQuery requestQuery: arrayOption){
            apiRequest.addRequestQuery(requestQuery);
        }
    }
}
