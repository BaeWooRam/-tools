package com.onedtwod.illuwa.util.login.kakao

import android.app.Activity
import com.onedtwod.illuwa.util.login.SnsLogin
import com.kakao.auth.AuthType
import com.kakao.auth.Session
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.UserManagement
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.UserProfileChangeRequest
import com.kakao.auth.ISessionCallback
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.callback.UnLinkResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.onedtwod.illuwa.model.type.LoginType
import java.util.ArrayList

/**
 * 날짜 : 2019-09-14
 * 작성자 : 배우람
 * 기능 : KaKaO 로그인
 */
abstract class KaKaOLogin(val activity: Activity): SnsLogin, SnsLogin.Common, SnsLogin.Kakao{
    private val Tag = javaClass.simpleName
    private val sessionCallback : ISessionCallback? =  object : ISessionCallback{
        override fun onSessionOpened() {
            Log.d(Tag, "SessionCallback signInWithCredential:success")
            requestMe()
        }

        // 로그인에 실패한 상태
        override fun onSessionOpenFailed(exception: KakaoException) {
            Log.e(Tag, "SessionCallback onSessionOpenFailed : ${exception.message}")
        }
    }
    private val session = Session.getCurrentSession()
    private var auth = FirebaseAuth.getInstance()


    override fun login() {

        Log.d(Tag,"Open {${session.isOpened}}")
        Log.d(Tag,"Closing {${session.isClosed}}")
        Log.d(Tag,"Openable {${session.isOpenable}}")

        if(!session.isOpened){
            session.addCallback(sessionCallback)
            session.open(AuthType.KAKAO_LOGIN_ALL, activity)
        }else{
            requestMe()
        }
    }

    override fun logout() {
        UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
            override fun onCompleteLogout() {
                auth.signOut()

            }
        })
    }

    override fun disconnectlogin() {
        UserManagement.getInstance().requestUnlink(object : UnLinkResponseCallback() {
            override fun onFailure(errorResult: ErrorResult?) {
                Log.e(Tag,"onFailure ${errorResult!!}")
            }

            override fun onSessionClosed(errorResult: ErrorResult) {
                Log.e(Tag,"onSessionClosed $errorResult")
            }

            override fun onNotSignedUp() {
                //TODO onNotSignedUp
            }

            override fun onSuccess(userId: Long?) {
                auth.signOut()
            }
        })
    }


    override fun setupKakaoOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
    }

    // 사용자 정보 요청
    fun requestMe() {
        val keys = ArrayList<String>()
        keys.add("properties.nickname")
        keys.add("properties.profile_image")
        keys.add("kakao_account.email")

        Log.d(Tag,"Open {${session.isOpened}}")
        Log.d(Tag,"Closing {${session.isClosed}}")
        Log.d(Tag,"Openable {${session.isOpenable}}")

        UserManagement.getInstance().me(keys, object : MeV2ResponseCallback() {
            override fun onFailure(errorResult: ErrorResult?) {
                val message = "failed to get user info. msg= {$errorResult}"
                Log.d(Tag,message)
            }

            override fun onSessionClosed(errorResult: ErrorResult) {
                //                redirectLoginActivity();
            }

            override fun onSuccess(response: MeV2Response) {
                Log.d(Tag,"id: ${response.nickname}")
                Log.d(Tag,"email: ${response.kakaoAccount.email}")
                Log.d(Tag,"profile: ${response.profileImagePath}")

                firebaseAuthWithKakao(
                    response.kakaoAccount.email,
                    response.id.toString(),
                    response.nickname,
                    response.profileImagePath)
            }
        })
    }

    fun removeCallBack(){
        Session.getCurrentSession().removeCallback(sessionCallback)
    }
    override fun firebaseAuthWithKakao(
        email: String,
        password: String,
        nickName: String,
        imageProfile: String
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d(Tag, "signInWithEmail:success")
                    handleSuccessFirebaseLogin(nickName,imageProfile)

                } else {
                    Log.w(Tag, "signInWithEmail:failure", task.exception)
                    if(task.exception is FirebaseAuthInvalidUserException)
                        createFirebaseUser(email,password)
                }
            }
    }

    fun handleSuccessFirebaseLogin(nickName: String, imageProfile: String){
        val user = auth.currentUser
        val requestUserProflie =  UserProfileChangeRequest.Builder()
            .setDisplayName(nickName)
            .setPhotoUri(Uri.parse(imageProfile))
            .build()

        user?.updateProfile(requestUserProflie)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateUser(user)
                    removeCallBack()
                }else{
                    Log.w(Tag, "Fail update UserProflie", task.exception)
                }
            }
    }

    fun createFirebaseUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Tag, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUser(user)
                } else {
                    Log.w(Tag, "createUserWithEmail:failure", task.exception)
                }
            }
    }

    override fun getLoginType(): LoginType {
        return LoginType.Kakao
    }
}