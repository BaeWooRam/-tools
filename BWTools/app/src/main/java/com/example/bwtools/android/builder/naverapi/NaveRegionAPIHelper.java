package com.example.bwtools.android.builder.naverapi;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class NaveRegionAPIHelper {
    private final static String TAG = "NaverAPIHelper";
    private final static int MAX_DISPLAY = 30;
    private String mSearchText, mClientID, mClientSecret,mSortAddress,mSortCategory;
    private int mStartCount=0, mdisplayCount=0;
    private MvpAdapter mAdapter;

    public NaveRegionAPIHelper setSortAddress(String mSortAddress) {
        this.mSortAddress = mSortAddress;
        return this;
    }

    public NaveRegionAPIHelper setSortCategory(String mSortCategory) {
        this.mSortCategory = mSortCategory;
        return this;
    }

    public NaveRegionAPIHelper setSearchText(String mSearchText) {
        this.mSearchText = mSearchText;
        return this;
    }

    public NaveRegionAPIHelper setClientID(String mClientID) {
        this.mClientID = mClientID;
        return this;
    }

    public NaveRegionAPIHelper setClientSecret(String mClientSecret) {
        this.mClientSecret = mClientSecret;
        return this;
    }

    public NaveRegionAPIHelper setStartCount(int mStartCount) {
        this.mStartCount = mStartCount;
        return this;
    }

    public NaveRegionAPIHelper setDisplayCount(int mSdisplayCount) {
        this.mdisplayCount = mSdisplayCount;
        return this;
    }

    public NaveRegionAPIHelper setAdapter(MvpAdapter mAdapter) {
        this.mAdapter = mAdapter;
        return this;
    }

    /**
     * 블로그 데이터 가져오기
     *  @param searchText 검색할 텍스트 입력
     *  @param ClientID 네이버 API 클라이언트 아이디 입력
     *  @param ClientSecret 네이버 API 클라이언트 시크릿 입력
     *  @return 블로그API로 검색한 결과
     */

    public String getBolgData(final String searchText, final String ClientID, final String ClientSecret) {
        try {
            String text = URLEncoder.encode(searchText, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/blog?query="+ text; // json 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-ClientID-Id", ClientID);
            con.setRequestProperty("X-Naver-Client-Secret", ClientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            return response.toString();
        } catch (Exception e) {
            System.out.println(TAG+" Error! "+e.toString());
            return null;
        }

    }

    /**
     *  지역 정보 가져오기
     *  @param searchText 검색할 텍스트 입력
     *  @param ClientID 네이버 API 클라이언트 아이디 입력
     *  @param ClientSecret 네이버 API 클라이언트 시크릿 입력
     *  @param displayCount 보여줄 데이터 입력
     *  @param startCount 보여줄 데이터의 시작 번호 입력
     *
     *  @return 지역API로 검색한 결과
     */
    public String getRegionData(final String searchText, final String ClientID, final String ClientSecret, final int displayCount, final int startCount){
        try {
            String text = URLEncoder.encode(searchText, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + text; // json 결과
            apiURL+="&display=" + displayCount;
            apiURL+="&start=" + startCount;
            apiURL+="&sort=" + "comment";

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", ClientID);
            con.setRequestProperty("X-Naver-Client-Secret", ClientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (Exception e) {
            System.out.println(TAG+" Error! "+e.toString());
            return null;
        }
    }

    /**
     *  지역 정보 가져오기(Display 30개 씩) -> 30개가 최대 사이즈
     *
     *  @param searchText 검색할 텍스트 입력
     *  @param ClientID 네이버 API 클라이언트 아이디 입력
     *  @param ClientSecret 네이버 API 클라이언트 시크릿 입력
     *  @param startCount 보여줄 데이터의 시작 번호 입력
     *
     *  @return 지역API로 검색한 결과
     */

    public String getRegionData(final String searchText, final String ClientID, final String ClientSecret, final int startCount){
        try {
            String text = URLEncoder.encode(searchText, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + text; // json 결과
            apiURL+="&display=" + MAX_DISPLAY;
            apiURL+="&start=" + startCount;
            apiURL+="&sort=" + "comment";
            Log.e(TAG,"URL= "+apiURL);

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", ClientID);
            con.setRequestProperty("X-Naver-Client-Secret", ClientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (Exception e) {
            System.out.println(TAG+" Error! "+e.toString());
            return null;
        }
    }


    /**
     *  결과값 전체 갯수
     *  @param total 파싱할 전체 갯수를 받는다
     *  @return 반복문 돌릴 값을 받는다.
     */
    private int getRepetitionValue(int total){
        int quotient = total / 30;
        int remainder = total % 30;
        if(remainder>0)
            quotient+=1;

        return quotient;
    }

    /**
     *  결과값 전체 갯수
     *  @param result 파싱되기전 결과 값을 받는다
     *  @return 전체 갯수 값이 없으면 0, 있으면 int형 반환
     */
    private int getRegionTotal(String result){

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(result).getAsJsonObject();
        String total;
        try {
            total = jsonObject.get("total").getAsString();
        }catch (Exception e){
            total = null;
        }

        return total != null ? Integer.parseInt(total) : 0;
    }

    /**
     *  결과값 파싱
     *  @param result 파싱되기전 결과 값을 받는다
     *  @return 결과 값이 없으면 null, 있으면 NavarRegion 형태로 반환
     */
    private NaverRegion[] parserRegionDataList(String result){

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        NaverRegion[] naverLocals;

        try {
            JsonObject jsonObject = jsonParser.parse(result).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("items");
            naverLocals = gson.fromJson(jsonArray, NaverRegion[].class);
        }catch (Exception e){
            naverLocals = null;
        }

        return naverLocals;
    }

    /**
     *  결과 값 분류
     *  @param list 네이버 지역 데이터
     *  @param sortCategory 분류할 카테고리 text 입력
     *  @param sortAddress 분류할 주소 text 입력
     *  @return 들어온 값이 없으면 null, 있으면 ArrayList<NaverRegion> 형태로 반환
     */
    private ArrayList<NaverRegion> sortRegionData(NaverRegion[] list, String sortCategory, String sortAddress){
        if(list==null)
            return null;

        ArrayList<NaverRegion> temp = new ArrayList<>();

        for(NaverRegion i : list){
            if(i.getAddress().contains(sortAddress) && (i.getCategory().contains(sortCategory) || i.getDescription().contains(sortCategory))){
                temp.add(i);
                System.out.println("Title : "+i.getTitle());
                System.out.println("Description : "+i.getDescription());
                System.out.println("InternetURL : "+i.getInternetURL());
                System.out.println("Category : "+i.getCategory()+"\n\n");
            }

        }

        return temp;
    }

    /**
     *  결과 값 분류
     *  @param list 네이버 지역 데이터
     *  @return 들어온 값이 없으면 null, 있으면 ArrayList<NaverRegion> 형태로 반환
     */
    private ArrayList<NaverRegion> sortAllRegionData(NaverRegion[] list){
        if(list==null)
            return null;

        ArrayList<NaverRegion> temp = new ArrayList<>();

        for(NaverRegion i : list){
            temp.add(i);
            System.out.println("Title : "+i.getTitle());
            System.out.println("Description : "+i.getDescription());
            System.out.println("InternetURL : "+i.getInternetURL());
            System.out.println("Category : "+i.getCategory()+"\n\n");

        }

        return temp;
    }


    private class applayAllRegionData extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            if (mSearchText !=null && mClientID != null && mClientSecret != null && mStartCount > 0)
                new Error(TAG+" Error! Please input SearchText, ClientID, ClientSecret and StartCount");
            return getRegionData(mSearchText,mClientID,mClientSecret,mStartCount);
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(TAG+"result = "+result);
            mAdapter.addList(sortAllRegionData(parserRegionDataList(result)));
        }
    }

    private class applayRegionData extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            if (mSearchText !=null && mClientID != null && mClientSecret != null && mStartCount > 0)
                new Error(TAG+" Error! Please input SearchText, ClientID, ClientSecret and StartCount");
            return getRegionData(mSearchText,mClientID,mClientSecret,mStartCount);
        }

        @Override
        protected void onPostExecute(String result) {
            if (mSortCategory !=null && mSortAddress != null)
                new Error(TAG+" Error! Please input SortAddress, SortCategory");
            mAdapter.addList(sortRegionData(parserRegionDataList(result),mSortCategory,mSortAddress));
        }
    }



    public void AllRegionData(){
        applayAllRegionData applayAllRegionData = new applayAllRegionData();
        applayAllRegionData.execute();
    }

    public void RegionData(){
        applayRegionData applayRegionData = new applayRegionData();
        applayRegionData.execute();
    }
}
