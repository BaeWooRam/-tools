package com.example.bwtools.android.login.oauth

import android.app.Activity
import android.util.Log

abstract class OAuthLogin(activity: Activity) :
    OAuth {
    private var oAuth:Array<BaseOAuth>? = null
    var loginType = OAuth.Type.NONE

    abstract fun settingOAuth():Array<BaseOAuth>
    override fun login() {
        checkOAuth()

        if (checkLoginType())
            oAuth!![loginType.value - 1].login()
    }

    override fun logout() {
        checkOAuth()

        if (checkLoginType())
            oAuth!![loginType.value - 1].logout()
    }

    override fun disconnect() {
        checkOAuth()

        if (checkLoginType())
            oAuth!![loginType.value - 1].disconnect()
    }

    fun allDisconnect(){
        checkOAuth()

        for (instance in oAuth!!)
            instance.disconnect()
    }

    override fun init() {
        oAuth = settingOAuth()
        checkOAuth()

        for (instance in oAuth!!)
            instance.init()
    }

    override fun clear() {
        checkOAuth()

        for (instance in oAuth!!)
            instance.clear()
    }

    /**
     * oAuth login handler
     */
    fun oAuthLoginListener(listener: OAuth.HandleLoginListener) {
        for (instance in oAuth!!) {
            instance.handleLoginListener = listener
        }
    }

    fun <T : OAuth> getLoginInstance(): T {
        checkLoginType()
        return oAuth!![loginType.value - 1] as T
    }

    @Throws(NullPointerException::class)
    private fun checkOAuth(){
        if(oAuth == null){
            throw NullPointerException("OAuth is Null!")
        }
    }

    private fun checkLoginType(): Boolean {
        return loginType.value != OAuth.Type.NONE.value
    }
}