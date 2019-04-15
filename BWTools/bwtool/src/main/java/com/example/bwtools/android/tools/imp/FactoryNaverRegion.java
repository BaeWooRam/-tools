package com.example.bwtools.android.tools.imp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.example.bwtools.R;
import com.example.bwtools.android.tools.interfaces.NaverLocalImp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import androidx.annotation.LayoutRes;


//TODO 현재 안드로이드에서 라이브러리로 분리작업
public class FactoryNaverRegion implements NaverLocalImp {
    private final String TAG = "NaverAPIHelper";
    public final int MAX_DISPLAY = 30;
    private String searchText, ClientID, ClientSecret, sortAddress, sortCategory;
    private int startCount = 1, displayCount = 0, count =0;
    private MvpAdapter Adapter;
    private Activity targetActivity;
    private ProgressDialog progressDialog;
    private int progressDialogLayout;
    public FactoryNaverRegion(Activity thisActivity,@LayoutRes int progressDialogLayout) {
        this.targetActivity = thisActivity;
        this.progressDialogLayout = progressDialogLayout;
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

    public FactoryNaverRegion setupSortCategory(String sortCategory) {
        this.sortCategory = (sortCategory == null) || sortCategory.equals("") ? "" : sortCategory;
        return this;
    }

    public FactoryNaverRegion setupStartCount(int startCount) {
        this.startCount = startCount <= 0 ? 1 : startCount;
        return this;
    }

    @Override
    public void setupClientInfo() {
        ClientID = (String) targetActivity.getText(R.string.naver_local_client_id);
        ClientSecret = (String) targetActivity.getText(R.string.naver_local_client_secret);
    }

    @Override
    public void insertAllRegionDataAfterHandleResult() {
        applayAllRegionData applayAllRegionData = new applayAllRegionData();
        applayAllRegionData.execute();
    }

    @Override
    public void insertRegionDataAfterHandleResult() {
        applayRegionData applayRegionData = new applayRegionData();
        applayRegionData.execute();
    }

    /**
     * 결과 값 분류
     *
     * @param array 네이버 지역 데이터
     * @return 들어온 값이 없으면 null, 있으면 ArrayList<NaverRegion> 형태로 반환
     */
    @Override
    public ArrayList<NaverRegion> sortAllRegionArrayToList(ArrayList<NaverRegion> array) {
        if (isEmptyNaverRegionArray(array))
            return null;

        ArrayList<NaverRegion> temp = new ArrayList<>();

        for (NaverRegion region : array) {
            if (region.isContainAddress(sortAddress)) {
                temp.add(region);
            }
        }

        return temp;
    }

    /**
     * 결과 값 분류
     *
     * @param array 네이버 지역 데이터
     * @return 들어온 값이 없으면 null, 있으면 ArrayList<NaverRegion> 형태로 반환
     */
    @Override
    public ArrayList<NaverRegion> sortRegionArrayToList(ArrayList<NaverRegion> array) {
        if (isEmptyNaverRegionArray(array))
            return null;

        ArrayList<NaverRegion> temp = new ArrayList<>();

        for (NaverRegion region : array) {
            if (region.isContainAddress(sortAddress) && region.isContainCategoryOrDescription(sortCategory)) {
                temp.add(region);
            }
        }

        return temp;
    }


    /**
     * 결과값 파싱
     *
     * @param result 파싱되기전 결과 값을 받는다
     * @return 결과 값이 없으면 null, 있으면 NavarRegion 형태로 반환
     */
    @Override
    public ArrayList<NaverRegion> parserRegionArray(String result) {
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();

        ArrayList<NaverRegion> naverRegionsList = new ArrayList<>();
        try {
            JsonObject jsonObject = jsonParser.parse(result).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("items");
            for (int i = 0; i < jsonArray.size(); i++,count++) {
                JsonObject jsonNaverRegions = (JsonObject) jsonArray.get(i);
                NaverRegion naverRegion = new NaverRegion();
                naverRegion.setNum(count);
                naverRegion.setAddress(jsonNaverRegions.get("address").getAsString());
                naverRegion.setCategory(jsonNaverRegions.get("category").getAsString());
                naverRegion.setDescription(jsonNaverRegions.get("description").getAsString());
                naverRegion.setTitle(jsonNaverRegions.get("title").getAsString());
                naverRegion.setTelephone(jsonNaverRegions.get("telephone").getAsString());
                naverRegion.setInternetURL(jsonNaverRegions.get("link").getAsString());
                naverRegionsList.add(naverRegion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return naverRegionsList;
    }

    /**
     * 결과값 전체 갯수
     *
     * @param result 파싱되기전 결과 값을 받는다
     * @return 전체 갯수 값이 없으면 0, 있으면 int형 반환
     */
    @Override
    public int getRegionTotal(String result) {
        Gson gson = new Gson();
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

    /**
     * 페이징을 위한 반복 횟수 계산
     *
     * @param total 파싱할 전체 갯수를 받는다
     * @return 반복문 돌릴 값을 받는다.
     */
    @Override
    public int getRepetitionCount(int total) {
        int RepetitionCount = total / MAX_DISPLAY;
        int remainder = total % MAX_DISPLAY;
        if (isRemainder(remainder))
            RepetitionCount += 1;

        return RepetitionCount;
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
            apiURL += "&display=" + MAX_DISPLAY;
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


    private class applayAllRegionData extends AsyncTask<Void, Void, ArrayList<NaverRegion> > {
        @Override
        protected void onPreExecute() {
            count =0;
            showLoadingDialog();
        }

        @Override
        protected ArrayList<NaverRegion>  doInBackground(Void... voids) {
            String result = getRegionSearchResult();
            int total = getRegionTotal(result);
            int repet = getRepetitionCount(total);
            ArrayList<NaverRegion> naverRegionList = sortAllRegionArrayToList(parserRegionArray(result));
            for(int i=1; i<=repet-1; i++){
                startCount=i*MAX_DISPLAY+1;
                result = getRegionSearchResult();
                for(NaverRegion naverRegion:sortAllRegionArrayToList(parserRegionArray(result))){
                    naverRegionList.add(naverRegion);
                }

            }

            return naverRegionList;
        }

        @Override
        protected void onPostExecute(ArrayList<NaverRegion> naverRegionList) {
            Adapter.addList(naverRegionList);
            hideLoading();
        }
    }

    private class applayRegionData extends AsyncTask<Void, Void, ArrayList<NaverRegion>> {
        @Override
        protected void onPreExecute() {
            count =0;
            showLoadingDialog();
        }

        @Override
        protected ArrayList<NaverRegion>  doInBackground(Void... voids) {
            String result = getRegionSearchResult();
            int total = getRegionTotal(result);
            int repet = getRepetitionCount(total);

            ArrayList<NaverRegion> naverRegionList = sortAllRegionArrayToList(parserRegionArray(result));
            for(int i=1; i<=repet-1; i++){
                startCount=i*MAX_DISPLAY+1;
                result = getRegionSearchResult();
                for(NaverRegion naverRegion:sortAllRegionArrayToList(parserRegionArray(result))){
                    naverRegionList.add(naverRegion);
                }

            }

            return naverRegionList;
        }

        @Override
        protected void onPostExecute(ArrayList<NaverRegion>  naverRegionList) {
            Adapter.addList(naverRegionList);
            hideLoading();
        }
    }

    private boolean isRemainder(int remainder) {
        return remainder > 0 ? true : false;
    }


    private boolean isEmptyNaverRegionArray(ArrayList<NaverRegion> regions) {
        return regions == null ? true : false;
    }

    public ProgressDialog showLoadingDialog() {
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(progressDialogLayout);
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
