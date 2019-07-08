package com.example.bwtools.android.tools.imp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.bwtools.R;
import com.example.bwtools.android.tools.interfaces.NaverLoginImp;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

public abstract class FactoryNaverLogin implements NaverLoginImp {
    private final String TAG = "FactoryNaverLogin";

    private String OAUTH_CLIENT_ID;
    private String OAUTH_CLIENT_SECRET;
    private String OAUTH_CLIENT_NAME;

    private final static OAuthLogin OAuthLoginInstance = OAuthLogin.getInstance();
    private Activity targetActivity;
    /**
     * startOAuthLoginActivity() 호출시 인자로 넘기거나, OAuthLoginButton 에 등록해주면 인증이 종료되는 걸 알 수 있다.
     */
    private OAuthLoginHandler OAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                RequestUserInfo();
            } else {
                String errorCode = OAuthLoginInstance.getLastErrorCode(targetActivity).getCode();
                String errorDesc = OAuthLoginInstance.getLastErrorDesc(targetActivity);
                Toast.makeText(targetActivity, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }

    };

    public OAuthLoginHandler getOAuthLoginHandler() {
        return OAuthLoginHandler;
    }

    public OAuthLogin getOAuthLoginInstance(){
        return this.OAuthLoginInstance;
    }

    public FactoryNaverLogin(Activity thisActivity) {
        this.targetActivity = thisActivity;
    }

    /**
     *  client 정보를 넣어주고, 인증 버튼 초기화
     */


    @Override
    public void setupNaverOAuthLoginInstance(OAuthLogin OAuthLoginInstance){
        OAuthLoginInstance.showDevelopersLog(true);
        OAuthLoginInstance.init(targetActivity, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
    }


    @Override
    public void logOut() {
        new NaverLogOutTask().execute();
    }

    @Override
    public void RequestUserInfo() {
        new RequestApiTask().execute();
    }

    @Override
    public void RefreshUserInfo() {
        new RefreshTokenTask().execute();
    }

    @Override
    public void Disconnect() {
        new DeleteTokenTask().execute();
    }

    @Override
    public void setupClientInfo() {
        OAUTH_CLIENT_NAME = (String)targetActivity.getText(R.string.app_name);
        OAUTH_CLIENT_ID = (String)targetActivity.getText(R.string.naver_login_client_id);
        OAUTH_CLIENT_SECRET = (String)targetActivity.getText(R.string.naver_login_client_secret);
    }

    /**
     *  현재 애플리케이션과 네이버 로그인 연동을 끊는다.
     */
    private class DeleteTokenTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            boolean isSuccessDeleteToken = OAuthLoginInstance.logoutAndDeleteToken(targetActivity);

            if (!isSuccessDeleteToken) {
                // 서버에서 token 삭제에 실패했어도 클라이언트에 있는 token 은 삭제되어 로그아웃된 상태이다
                // 실패했어도 클라이언트 상에 token 정보가 없기 때문에 추가적으로 해줄 수 있는 것은 없음
                Log.d(TAG, "errorCode:" + OAuthLoginInstance.getLastErrorCode(targetActivity));
                Log.d(TAG, "errorDesc:" + OAuthLoginInstance.getLastErrorDesc(targetActivity));
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
            OAuthLoginInstance.logout(targetActivity);

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
            String at = OAuthLoginInstance.getAccessToken(targetActivity);
            return OAuthLoginInstance.requestApi(targetActivity, at, url);
        }

        protected void onPostExecute(String content) {
            RequestUserInfoAfterUI(content);
        }
    }

    /**
     *  액세스 토큰 갱신, 갱신된 토큰은 Instance에 적용되므로 따로 처리 안함
     */
    private class RefreshTokenTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            OAuthLoginInstance.refreshAccessToken(targetActivity);
            return null;
        }
    }
}
