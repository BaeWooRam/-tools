package com.example.bwtools.android.login.oauth

abstract class BaseOAuth : OAuth {
    var handleLoginListener: OAuth.HandleLoginListener? = null
}