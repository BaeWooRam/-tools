package com.onedtwod.illuwa.util.login

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.onedtwod.illuwa.model.type.LoginType

/**
 * 날짜 : 2019-09-14
 * 작성자 : 배우람
 * 기능 : 통합 로그인 인터페이스
 */
interface SnsLogin {
    fun login()
    fun logout()
    fun disconnectlogin()
    fun getLoginType():LoginType

    interface Common{
        fun updateUser(user: FirebaseUser?)
    }

    interface Google{
        fun firebaseAuthWithGoogle(acct: GoogleSignInAccount)
        fun setupFirebaseLoginOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }

    interface Kakao{
        fun firebaseAuthWithKakao(email : String, password:String, nickName : String, imageProfile:String)
        fun setupKakaoOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }
}