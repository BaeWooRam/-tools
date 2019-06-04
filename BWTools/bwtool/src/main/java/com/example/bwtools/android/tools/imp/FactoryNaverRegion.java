package com.example.bwtools.android.tools.imp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;

import com.example.bwtools.R;
import com.example.bwtools.android.tools.base.dto.NaverRegion;
import com.example.bwtools.android.tools.base.mvp.MvpAdapter;
import com.example.bwtools.android.tools.interfaces.NaverLocalImp;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import androidx.annotation.LayoutRes;


public class FactoryNaverRegion implements NaverLocalImp {
    private final String TAG = "FactoryNaverRegion";
    public static final int MAX_REGION_DATA =30;
    public static final int MAX_DISPLAY =10;
    private String searchText, ClientID, ClientSecret, sortAddress, sortCategoryAndDescription, requestResult;
    private int startCount = 1, position = 0, page = 0;

    private MvpAdapter<NaverRegion> Adapter;
    private Activity targetActivity;
    private ProgressDialog progressDialog;
    public FactoryNaverRegion(Activity thisActivity, @LayoutRes int progressDialogLayout) {
        this.targetActivity = thisActivity;
        this.progressDialog = new ProgressDialog(targetActivity);
    }

    /**
     * MvpAdapter를 상속받은 어댑터만 가능
     */
    public FactoryNaverRegion setupAdapter(MvpAdapter adapter) {
        Adapter = adapter;
        return this;
    }

    public FactoryNaverRegion setupSearch(String search) {
        searchText = (search == null) || search.equals("") ? "" : search;
        return this;
    }

    public FactoryNaverRegion setupSortAddress(String sortAddress) {
        this.sortAddress = (sortAddress == null) || sortAddress.equals("") ? "" : sortAddress;
        return this;
    }

    public FactoryNaverRegion setupSortCategoryAndDescription(String sortCategory) {
        this.sortCategoryAndDescription = (sortCategory == null) || sortCategory.equals("") ? "" : sortCategory;
        return this;
    }

    public FactoryNaverRegion setupStartCount(int startCount) {
        this.startCount = startCount <= 0 ? 1 : startCount;
        return this;
    }

    public void setupRequestResult(String requestResult) {
        this.requestResult = requestResult;
    }

    @Override
    public void setupClientInfo() {
        ClientID = (String) targetActivity.getText(R.string.naver_local_client_id);
        ClientSecret = (String) targetActivity.getText(R.string.naver_local_client_secret);
    }

    public void setupClientIDAndSecret(String clientID,String clientSecret) {
        ClientID = clientID;
        ClientSecret = clientSecret;
    }



    /**
     * 지역 정보 가져오기(Display 30개 씩) -> 30개가 최대 사이즈
     *
     * @return 지역API로 검색한 결과
     */
    @Override
    public String getRegionSearchResult() {
        try {
            String text = URLEncoder.encode(searchText, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + text; // json 결과
            apiURL += "&display=" + MAX_REGION_DATA;
            apiURL += "&start=" + startCount;
            apiURL += "&sort=" + "comment";

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
            System.out.println(TAG + " Error! " + e.toString());
            return null;
        }
    }


    @Override
    public void insertRegionData() {
        applayinsertRegionData applayAllRegionData = new applayinsertRegionData();
        applayAllRegionData.execute();
    }

    @Override
    public void AddRegionData() {
        applayAddRegionData applayRegionData = new applayAddRegionData();
        applayRegionData.execute();
    }

    private class applayinsertRegionData extends AsyncTask<Void, Void, ArrayList<NaverRegion> > {
        long startTime;
        @Override
        protected void onPreExecute() {
            showLoadingDialog();
        }

        @Override
        protected ArrayList<NaverRegion>  doInBackground(Void... voids) {
            startTime = System.currentTimeMillis();

            requestResult = getRegionSearchResult();
            ArrayList<NaverRegion> parserRegionList = getParserRegionList();
            handleAndGetRegionData(parserRegionList);

            return parserRegionList;
        }

        @Override
        protected void onPostExecute(ArrayList<NaverRegion> naverRegionList) {
            Adapter.addList(naverRegionList);
            hideLoading();

            System.out.println("걸린시간(밀리초) = "+ (System.currentTimeMillis()-startTime));
        }
    }

    private class applayAddRegionData extends AsyncTask<Void, Void, ArrayList<NaverRegion>> {
        long startTime;
        @Override
        protected void onPreExecute() {
            showLoadingDialog();
        }

        @Override
        protected ArrayList<NaverRegion>  doInBackground(Void... voids) {
            startTime = System.currentTimeMillis();

            ArrayList<NaverRegion> parserRegionList = new ArrayList<>();
            handleAndGetRegionData(parserRegionList);

            return parserRegionList;
        }

        @Override
        protected void onPostExecute(ArrayList<NaverRegion>  naverRegionList) {
            Adapter.addList(naverRegionList);
            hideLoading();
            System.out.println("걸린시간(밀리초) = "+ (System.currentTimeMillis()-startTime));
        }
    }

    public void handleAndGetRegionData(ArrayList<NaverRegion> parserRegionList){
        int total = getRegionTotal(requestResult);
        int MaxPage = total / MAX_REGION_DATA;
        int MaxPostion = (total % MAX_REGION_DATA) -1;

        while(!(parserRegionList.size()>=MAX_DISPLAY)){
            if(page == MaxPage && position == MaxPostion)
                break;
            else{
                if(position>=(MAX_REGION_DATA-1)){
                    page++;
                    position = 0;
                    startCount=page*MAX_REGION_DATA+1;
                    requestResult = getRegionSearchResult();
                }
            }
            restartParserRegionArray(parserRegionList);
        }
    }


    public int getRegionTotal(String result) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(result).getAsJsonObject();
        String total;
        try {
            total = jsonObject.get("total").getAsString();
        } catch (Exception e) {
            total = null;
        }

        return total != null ? Integer.parseInt(total) : 0;
    }

    public void restartParserRegionArray(ArrayList<NaverRegion> naverRegionsList) {

        try {
            JsonArray RegionArray = getRegionJsonArray(requestResult);
            for (int restart = position+1 ; restart < RegionArray.size(); restart++) {
                insertRegionListFromMatchingItem(RegionArray, naverRegionsList,restart);
                if (isMaxSize(naverRegionsList)){
                    position = restart;
                    break;
                }
                position = restart;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<NaverRegion> getParserRegionList() {
        ArrayList<NaverRegion> naverRegionsList = new ArrayList<>();
        try {
            JsonArray RegionArray = getRegionJsonArray(requestResult);
            for (position =0 ; position < RegionArray.size(); position++) {
                insertRegionListFromMatchingItem(RegionArray, naverRegionsList,position);
                if (isMaxSize(naverRegionsList)){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return naverRegionsList;
    }

    public JsonArray getRegionJsonArray(String result){
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(result).getAsJsonObject();
        return jsonObject.getAsJsonArray("items");
    }

    public void insertRegionListFromMatchingItem(JsonArray item, ArrayList<NaverRegion> naverRegionsList, int position){
        JsonObject jsonNaverRegions = (JsonObject) item.get(position);
        String address = jsonNaverRegions.get("address").getAsString();
        String category = jsonNaverRegions.get("category").getAsString();

        if(isContainCategoryAndAddress(category,address)){
            naverRegionsList.add(insertNaverRegionInfo(jsonNaverRegions));
        }
    }

    public NaverRegion insertNaverRegionInfo(JsonObject jsonNaverRegions){
        NaverRegion naverRegion = new NaverRegion();
        naverRegion.setAddress(jsonNaverRegions.get("address").getAsString());
        naverRegion.setCategory(jsonNaverRegions.get("category").getAsString());
        naverRegion.setDescription(jsonNaverRegions.get("description").getAsString());
        naverRegion.setName(jsonNaverRegions.get("title").getAsString());
        naverRegion.setPhone(jsonNaverRegions.get("telephone").getAsString());
        naverRegion.setInternetURL(jsonNaverRegions.get("link").getAsString());
        return naverRegion;
    }

    public boolean isMaxSize(ArrayList<NaverRegion> naverRegionsList){
<<<<<<< HEAD
        if (naverRegionsList.size()>=MAX_DISPLAY){
            return true;
        }else
            return false;
=======

        return naverRegionsList.size()>=MAX_DISPLAY? true : false;
>>>>>>> parent of cd21003... Revert "06-04"
    }

    public boolean isContainAddress(String targetAddress) {
        if (targetAddress == null)
            return true;
        else{
            return targetAddress.contains(sortAddress);
        }
    }

    public boolean isContainDescription(String targetDescription) {
        if (targetDescription == null)
            return true;
        else{
            return targetDescription.contains(sortCategoryAndDescription);
        }
    }

    public boolean isContainCategory(String targetCategory) {
        if (targetCategory == null)
            return true;
        else{
            return targetCategory.contains(sortCategoryAndDescription);
        }
    }

    public boolean isContainCategoryOrDescription(String sortCategoryAndDescription) {
        return (isContainDescription(sortCategoryAndDescription) || isContainCategory(sortCategoryAndDescription) ? true : false);
    }

    public boolean isContainCategoryAndAddress(String targetCategoryAndDescription, String targetAddress) {
        return (isContainCategoryOrDescription(targetCategoryAndDescription) && isContainAddress(targetAddress) ? true : false);
    }

    public ProgressDialog showLoadingDialog() {
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

}
