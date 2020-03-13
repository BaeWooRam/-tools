package com.onedtwod.illuwa.util.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.onedtwod.illuwa.util.login.google.FirebaseGoogleLogin
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.onedtwod.illuwa.model.type.LoginType
import com.onedtwod.illuwa.util.login.kakao.KaKaOLogin

/**
 * 날짜 : 2019-09-14
 * 작성자 : 배우람
 * 기능 : 통합 로그인 헬퍼
 */
abstract class LoginHelpler(private var targetActivity: Activity) : SnsLogin, SnsLogin.Google, SnsLogin.Kakao{
    companion object{
        val GOOGLE = LoginType.Google
        val KAKAO = LoginType.Kakao
    }

    //로그인 필요 정보들
    private val TAG = javaClass.simpleName
    private var loginType : LoginType = LoginType.None
    private var loginObjectsList :Array<SnsLogin> =  arrayOf(
        object : FirebaseGoogleLogin(targetActivity){
            override fun updateUser(user: FirebaseUser?) {
                completeLogInAndOut(user)
            }
        }, object : KaKaOLogin(targetActivity){
            override fun updateUser(user: FirebaseUser?) {
                completeLogInAndOut(user)
            }
        })


    //로그인 요청 결과들
    var userInfo : FirebaseUser? = null
    var isLoginSuccess : Boolean = false

    abstract fun LoginAfterUI(user: FirebaseUser)
    abstract fun ErrorAfterUI()

    /**
     * 로그인 타입 정하기
     */
    fun loginType(type: LoginType):SnsLogin{
        this.loginType = type

        return this
    }

    /**
     * 사용자가 로그인 또는 로그아웃에 성공한 다음 실행
     */
    fun completeLogInAndOut(user: FirebaseUser?){
        if(user != null){
            isLoginSuccess = true
            userInfo = user
            LoginAfterUI(user)
        }else{
            isLoginSuccess = false
            ErrorAfterUI()
        }
    }

    /**
     * 사용자가 지정한 SnsLogin 방식 Util 가져오기
     */
    private fun getLoginUtil() : SnsLogin? {
        loginObjectsList.let {
            for(util in it){
                if(loginType == util.getLoginType())
                    return util
            }
        }

        return null
    }

    override fun disconnectlogin() {
        FirebaseAuth.getInstance().signOut()
        getLoginUtil()?.disconnectlogin()
    }

    override fun login() {
        isLoginSuccess = false
        getLoginUtil()?.login()
    }

    override fun logout() {
        isLoginSuccess = false
        getLoginUtil()?.logout()
    }

    override fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        (loginObjectsList.get(GOOGLE.ordinal) as FirebaseGoogleLogin).firebaseAuthWithGoogle(acct)
    }

    override fun firebaseAuthWithKakao(
        email: String,
        password: String,
        nickName: String,
        imageProfile: String
    ) {
        (loginObjectsList[KAKAO.ordinal] as KaKaOLogin).firebaseAuthWithKakao(email,password, nickName, imageProfile)
    }

    /**
     * Firebase로그인시 onActiviyResult 사용해야함(필수)
     */
    override fun setupFirebaseLoginOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        (loginObjectsList[GOOGLE.ordinal] as FirebaseGoogleLogin).setupFirebaseLoginOnActivityResult(requestCode,resultCode,data)
    }

    /**
     * KaKaO로그인시 onActiviyResult 사용해야함(필수)
     */
    override fun setupKakaoOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        (loginObjectsList[KAKAO.ordinal] as KaKaOLogin).setupKakaoOnActivityResult(requestCode,resultCode,data)
    }

    fun setupOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        if (resultCode != Activity.RESULT_OK) {
            Log.e("onActivityResult", "RESULT not OK")
            return
        }

        when (requestCode) {
            FirebaseGoogleLogin.FIREBASE_SIGN_IN -> {
                setupFirebaseLoginOnActivityResult(requestCode, resultCode, data)
            }

            else -> {
                setupKakaoOnActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun getLoginType(): LoginType {
        return loginType
    }
}