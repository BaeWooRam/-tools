package com.example.bwtools.android.tools.imp;

import com.example.bwtools.android.tools.base.dto.KaKaORegion;
import com.example.bwtools.android.tools.base.dto.Location;
import com.example.bwtools.android.tools.base.dto.Rect;
import com.example.bwtools.android.tools.base.dto.RequestHead;
import com.example.bwtools.android.tools.base.dto.RequestQuery;
import com.example.bwtools.android.tools.interfaces.KaKaOLocalImp;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class FactoryKaKaORegion implements KaKaOLocalImp {
    private final String REGION_SEARCH_QUERY="query";
    private final String REGION_SEARCH_CATEGORY_CODE="category_group_code";
    private final String REGION_SEARCH_LONGITUDE="x";
    private final String REGION_SEARCH_LATITUDE="y";
    private final String REGION_SEARCH_LOCATION_RANGE="rect";
    private final String REGION_SEARCH_PAGE="page";
    private final String REGION_SEARCH_DISPLAY_SIZE="size";
    private final String REGION_SEARCH_SORT="sort";
    private final String REGION_SEARCH_RADIUS="radius";
    private final String API_KEY_AUT="Authorization";


    public final String CATEGORY_CODE_MART="MT1";
    public final String CATEGORY_CODE_CONVENIENCE_STORE="CS2";
    public final String CATEGORY_CODE_PRESCHOOL="PS3";
    public final String CATEGORY_CODE_CULTURAL_FACILITIES="CT1";
    public final String CATEGORY_CODE_ACADEMY="AC5";

    public final String SORT_DISTANCE="distance";
    public final String SORT_ACCURACY="accuracy";

    private ApiRequest apiRequest;
    private ArrayList<KaKaORegion> kakaORegionsList;

    public int sdfa;
    public int asdfas;

    public FactoryKaKaORegion(String baseURL, String requestMethod) {
        this.apiRequest = new ApiRequest();
        this.apiRequest.setupRequestInfo(baseURL, requestMethod);
    }


    @Override
    public void setupBaseURLAndRequestMethod(String baseURL, String requestMethod) {
        apiRequest.setupRequestInfo(baseURL,requestMethod);
    }

    @Override
    public void setupKeyWord(String query) {
        RequestQuery requestQuery = new RequestQuery(REGION_SEARCH_QUERY, query,true);
        apiRequest.addRequestQuery(requestQuery);
    }

    @Override
    public void setupKeyWordAndCategoryCode(String query, String categoryCode) {
        RequestQuery arrayRequest[] ={new RequestQuery(REGION_SEARCH_QUERY,query, true),new RequestQuery(REGION_SEARCH_CATEGORY_CODE,categoryCode)};

        for(RequestQuery requestQuery: arrayRequest){
            apiRequest.addRequestQuery(requestQuery);
        }
    }

    @Override
    public void setupAuthorization(String kakaoApiKey) {
        RequestHead requestHeadAUT = new RequestHead(API_KEY_AUT, "KakaoAK "+kakaoApiKey);
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
    public void setupLocationRange(Location location, int radius) {
        RequestQuery arrayLocation[] = {new RequestQuery(REGION_SEARCH_LONGITUDE, location.getLocationLongitude()),new RequestQuery(REGION_SEARCH_LATITUDE,location.getLocationLatitude()), new RequestQuery(REGION_SEARCH_RADIUS,String.valueOf(radius))};

        for(RequestQuery requestQuery: arrayLocation){
            apiRequest.addRequestQuery(requestQuery);
        }
    }

    @Override
    public void setupLocationRange(Rect locationRange) {
        String range = locationRange.getLeftLongitude()+","+locationRange.getLeftLatitude()+","+locationRange.getRightLongitude()+","+locationRange.getRightLatitude();

        RequestQuery requestQuery = new RequestQuery(REGION_SEARCH_LOCATION_RANGE, range);
        apiRequest.addRequestQuery(requestQuery);
    }

    /**
     * @param pageNum 결과 페이지 번호(1-45 사이)
     * @param displayCount 한 페이지에 보여질 문서의 개수(1-15 사이)
     * @param sortMethod distance or accuracy
     */
    @Override
    public void setupRequestOption(int pageNum, int displayCount, String sortMethod) {
        RequestQuery arrayOption[] = {new RequestQuery(REGION_SEARCH_PAGE, Integer.toString(pageNum)),
                new RequestQuery(REGION_SEARCH_DISPLAY_SIZE,Integer.toString(displayCount)),
                new RequestQuery(REGION_SEARCH_SORT,sortMethod)};

        for(RequestQuery requestQuery: arrayOption){
            apiRequest.addRequestQuery(requestQuery);
        }
    }

    @Override
    public void startRequestQuery() {
        apiRequest.startRequest();
    }

    public String getResponse(){
        return apiRequest.getResponseResult();
    }

    @Override
    public void HandleResponseAfterRequest() {
        kakaORegionsList = getParserRegionList();
    }


    public ArrayList<KaKaORegion> getParserRegionList() {
        ArrayList<KaKaORegion> naverRegionsList = new ArrayList<>();
        try {

            JsonArray regionArray = getRegionJsonArray(getResponse());
            for (int position =0 ; position < regionArray.size(); position++) {
                JsonObject regionObject = (JsonObject) regionArray.get(position);
                naverRegionsList.add(insertNaverRegionInfo(regionObject));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return naverRegionsList;
    }

    public JsonArray getRegionJsonArray(String result){
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(result).getAsJsonObject();
        return jsonObject.getAsJsonArray("documents");
    }

    public KaKaORegion insertNaverRegionInfo(JsonObject jsonNaverRegions){
        KaKaORegion kaKaORegion = new KaKaORegion();
        kaKaORegion.setAddress(jsonNaverRegions.get("address").getAsString());
        kaKaORegion.setCategory(jsonNaverRegions.get("category").getAsString());
        kaKaORegion.setTitle(jsonNaverRegions.get("title").getAsString());
        kaKaORegion.setTelephone(jsonNaverRegions.get("telephone").getAsString());
        kaKaORegion.setInternetURL(jsonNaverRegions.get("link").getAsString());
        return kaKaORegion;
    }
}
