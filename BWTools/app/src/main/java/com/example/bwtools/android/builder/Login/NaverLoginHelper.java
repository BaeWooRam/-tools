package com.example.akginakwon.data.login;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.akginakwon.R;
import com.example.akginakwon.data.NaverAPIHelper;
import com.example.akginakwon.ui.common.AkgiApplication;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

public abstract class NaverLoginHelper {
    private static final String TAG = "OAuthSampleActivity";

    private static String OAUTH_CLIENT_ID;
    private static String OAUTH_CLIENT_SECRET;
    private static String OAUTH_CLIENT_NAME;

    private OAuthLogin mOAuthLoginInstance;
    private Activity mContext;
    private AkgiApplication mApplication;


    public NaverLoginHelper setContext(Activity mContext) {
        this.mContext = mContext;
        return this;
    }

    public NaverLoginHelper(Activity mContext, AkgiApplication application) {
        this.mContext = mContext;
        this.mApplication =application;
        this.mOAuthLoginInstance = application.getNaverLoginInstance();
        init();
    }

    /**
     *  client 정보를 넣어주고, 인증 버튼 초기화
     */
    private void init() {
        OAUTH_CLIENT_NAME = (String)mContext.getText(R.string.app_name);
        OAUTH_CLIENT_ID = (String)mContext.getText(R.string.naver_client_id);
        OAUTH_CLIENT_SECRET = (String)mContext.getText(R.string.naver_client_secret);

        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
    }

    /**
     *  현재 애플리케이션과 네이버 로그인 연동을 끊는다.
     */
    private class DeleteTokenTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            boolean isSuccessDeleteToken = mOAuthLoginInstance.logoutAndDeleteToken(mContext);

            if (!isSuccessDeleteToken) {
                // 서버에서 token 삭제에 실패했어도 클라이언트에 있는 token 은 삭제되어 로그아웃된 상태이다
                // 실패했어도 클라이언트 상에 token 정보가 없기 때문에 추가적으로 해줄 수 있는 것은 없음
                Log.d(TAG, "errorCode:" + mOAuthLoginInstance.getLastErrorCode(mContext));
                Log.d(TAG, "errorDesc:" + mOAuthLoginInstance.getLastErrorDesc(mContext));
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            LogOutAfterUI();
        }
    }

    /**
     *  네이버 로그아웃.
     */
    private class NaverLogOutTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            mOAuthLoginInstance.logout(mContext);

            return null;
        }

        protected void onPostExecute(Void v) {
            LogOutAfterUI();
        }
    }

    /**
     *  네이버로부터 프로필 정보 조회한다. 넘어온 JSON 데이터 처리작업 필수
     */
    private class RequestApiTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = mOAuthLoginInstance.getAccessToken(mContext);
            return mOAuthLoginInstance.requestApi(mContext, at, url);
        }

        protected void onPostExecute(String content) {
            RequestApiAfterUI(content);
        }
    }

    /**
     *  액세스 토큰 갱신, 갱신된 토큰은 Instance에 적용되므로 따로 처리 안함
     */
    private class RefreshTokenTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            mOAuthLoginInstance.refreshAccessToken(mContext);
            return null;
        }
    }

    /**
     * startOAuthLoginActivity() 호출시 인자로 넘기거나, OAuthLoginButton 에 등록해주면 인증이 종료되는 걸 알 수 있다.
     */
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                RequestUserInfo();
            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }

    };

    public OAuthLoginHandler getOAuthLoginHandler() {
        return mOAuthLoginHandler;
    }

    public void LogOut(){
        new NaverLogOutTask().execute();
    }

    public void RequestUserInfo(){
        new RequestApiTask().execute();
    }

    public void RefreshUserInfo(){
        new RefreshTokenTask().execute();
    }
    public void Disconnect(){
        new DeleteTokenTask().execute();
    }

    public abstract void LogOutAfterUI();
    public abstract void RequestApiAfterUI(String account);

}
