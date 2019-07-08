package com.example.bwtools.android.tools.imp;

import android.app.ProgressDialog;

import com.example.bwtools.android.tools.dto.Point;
import com.example.bwtools.android.tools.dto.Rect;
import com.example.bwtools.android.tools.dto.RequestHead;
import com.example.bwtools.android.tools.dto.RequestQuery;
import com.example.bwtools.android.tools.interfaces.KaKaOLocalImp;

public class FactoryKaKaORegion implements KaKaOLocalImp {
    public final String CATEGORY_CODE_MART="MT1";
    public final String CATEGORY_CODE_CONVENIENCE_STORE="CS2";
    public final String CATEGORY_CODE_PRESCHOOL="PS3";
    public final String CATEGORY_CODE_CULTURAL_FACILITIES="CT1";
    public final String CATEGORY_CODE_ACADEMY="AC5";

    public final String SORT_DISTANCE="distance";
    public final String SORT_ACCURACY="accuracy";

    private ApiRequest apiRequest;
    private ProgressDialog progressDialog;


    public FactoryKaKaORegion(String kakaoApiKey) {
        this.apiRequest = new ApiRequest();
        setupBaseURLAndRequestMethod();
        setupAuthorization(kakaoApiKey);
    }

    @Override
    public void setupBaseURLAndRequestMethod() {
        apiRequest.setupRequestInfo("https://dapi.kakao.com/v2/local/search/keyword.json", apiRequest.HttpCONNECTTION_GET);
    }

    @Override
    public void setupKeyWord(String query) {
        RequestQuery requestQuery = new RequestQuery("query", query,true);
        apiRequest.addRequestQuery(requestQuery);
    }

    @Override
    public void setupKeyWordAndCategoryCode(String query, String categoryCode) {
        RequestQuery arrayRequest[] ={new RequestQuery("query",query, true),new RequestQuery("category_group_code",categoryCode)};

        for(RequestQuery requestQuery: arrayRequest){
            apiRequest.addRequestQuery(requestQuery);
        }
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

    /**
     * @param radius 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 중심좌표로 쓰일 x,y와 함께 사용(단위 meter	0~20000)
     */
    @Override
    public void setupLocationRange(Point location, int radius) {
        RequestQuery arrayLocation[] = {new RequestQuery("x", String.valueOf(location.getLongitude())),
                new RequestQuery("y",String.valueOf(location.getLatitude())),
                new RequestQuery("radius",String.valueOf(radius))};

        for(RequestQuery requestQuery: arrayLocation){
            apiRequest.addRequestQuery(requestQuery);
        }
    }

    @Override
    public void setupLocationRange(Rect locationRange) {
        String range = locationRange.getLeftLongitude()+","+locationRange.getLeftLatitude()+","+locationRange.getRightLongitude()+","+locationRange.getRightLatitude();

        RequestQuery requestQuery = new RequestQuery("rect", range);
        apiRequest.addRequestQuery(requestQuery);
    }

    /**
     * @param pageNum 결과 페이지 번호(1-45 사이)
     * @param displayCount 한 페이지에 보여질 문서의 개수(1-15 사이)
     * @param sortMethod distance or accuracy
     */
    @Override
    public void setupRequestOption(int pageNum, int displayCount, String sortMethod) {
        RequestQuery arrayOption[] = {new RequestQuery("page", Integer.toString(pageNum)),
                new RequestQuery("size",Integer.toString(displayCount)),
                new RequestQuery("sort",sortMethod)};

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
