package com.example.bwtools.android.tools.imp;

import android.app.ProgressDialog;

import com.example.bwtools.android.tools.dto.RequestQuery;
import com.example.bwtools.android.tools.interfaces.AutoAddressImp;

public class FactorySearchAddress implements AutoAddressImp{
    private final String ADDRESS_TOTAL_COUNT="totalCount";
    private final String ADDRESS_ERROR_CODE="errorCode";
    private final String ADDRESS_ERROR_MESSAGE="errorMessage";

    public final String RESULT_TYPE_JSON="json";
    public final String RESULT_TYPE_XML="xml";

    public final String ERROR_CODE_SUCCES = "0";
    private ApiRequest apiRequest;
    private ProgressDialog progressDialog;

    public FactorySearchAddress(String comfirmKey) {
        this.apiRequest = new ApiRequest();
        setupBaseURLAndRequestMethod();
        setupAuthorization(comfirmKey);
    }

    @Override
    public void setupBaseURLAndRequestMethod() {
        apiRequest.setupRequestInfo("http://www.juso.go.kr/addrlink/addrLinkApi.do", apiRequest.HttpCONNECTTION_GET);
    }

    @Override
    public void setupKeyWord(String query) {
        RequestQuery requestQuery = new RequestQuery("keyword", query,true);
        apiRequest.addRequestQuery(requestQuery);
    }


    @Override
    public void setupAuthorization(String comfirmKey) {
        RequestQuery requestQuery = new RequestQuery("confmKey", comfirmKey,false);
        apiRequest.addRequestQuery(requestQuery);
    }

    @Override
    public void setupAutoClear(boolean doAutoClear) {
        apiRequest.setupDoAutoClearAfterRequest(doAutoClear);
    }

    /**
     * @param pageNum 결과 페이지 번호(Page > 0)
     * @param displayCount 한 페이지에 보여질 문서의 개수(1-100 사이)
     */
    @Override
    public void setupRequestOption(int pageNum, int displayCount, String resultType) {
        RequestQuery arrayOption[] = {new RequestQuery("currentPage", Integer.toString(pageNum)),
                new RequestQuery("countPerPage",Integer.toString(displayCount)),
                new RequestQuery("resultType", resultType)};

        for(RequestQuery requestQuery: arrayOption){
            apiRequest.addRequestQuery(requestQuery);
        }
    }

    @Override
    public void startRequestQuery() {
        apiRequest.startRequest();
    }


    @Override
    public String getResponse(){
        return apiRequest.getResponseResult();
    }


}
