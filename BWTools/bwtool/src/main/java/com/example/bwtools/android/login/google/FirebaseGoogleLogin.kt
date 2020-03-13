package com.onedtwod.illuwa.util.login.google

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.onedtwod.illuwa.util.login.SnsLogin
import com.onedtwod.illuwa.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.onedtwod.illuwa.model.type.LoginType

/**
 * 날짜 : 2019-09-14
 * 작성자 : 배우람
 * 기능 : 파이어베이스 구글 로그인
 */
abstract class FirebaseGoogleLogin(val activity : Activity) : SnsLogin, SnsLogin.Common, SnsLogin.Google{
    companion object{
        const val FIREBASE_SIGN_IN = 201
    }

    private val TAG = "FirebaseGoogleLogin"
    val googleSignInClient : GoogleSignInClient by lazy {
        val gso = getGoogleSignInOptions(activity)
        GoogleSignIn.getClient(activity, gso!!)
    }

    private fun getGoogleSignInOptions(context: Context) : GoogleSignInOptions?{
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    var auth = FirebaseAuth.getInstance()

    override fun login() {
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, FIREBASE_SIGN_IN)
    }

    override fun logout() {
        auth.signOut()

        googleSignInClient.signOut()?.addOnCompleteListener(activity) {
            updateUser(null)
        }
    }

    override fun disconnectlogin() {
        auth.signOut()

        googleSignInClient.revokeAccess().addOnCompleteListener(activity) {
            updateUser(null)
        }
    }

    override fun setupFirebaseLoginOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            Log.e("FIREBASE_SIGN_IN", "Google sign in failed" + account?.email)

            account?.apply {
                firebaseAuthWithGoogle(account)
            }
        } catch (e: ApiException) {
            Log.e("FIREBASE_SIGN_IN", "Google sign in failed", e)
        }
    }

    override fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e(TAG, "signInWithCredential:success")

                    auth.currentUser?.apply {
                        updateUser(this)
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    override fun getLoginType(): LoginType {
        return LoginType.Google
    }
}