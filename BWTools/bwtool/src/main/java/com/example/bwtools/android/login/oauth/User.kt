package com.example.bwtools.android.login.oauth

open class User(
    val uuid:String,
    val name:String,
    val email:String) {
    constructor():this("","","")
}