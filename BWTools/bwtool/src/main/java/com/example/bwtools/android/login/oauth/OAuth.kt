package com.example.bwtools.android.login.oauth

interface OAuth {
    enum class Type(val value: Int) {
        NONE(0), KAKAO(1), NAVER(2), FACEBOOK(3)
    }

    fun login()
    fun logout()
    fun disconnect()
    fun init()
    fun clear()

    interface HandleLoginListener{
        fun success(user: User)
        fun failure(errorMessage:String)
    }
}