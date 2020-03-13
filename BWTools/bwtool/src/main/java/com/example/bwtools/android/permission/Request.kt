package com.example.bwtools.android.permission

interface Request {
    fun requestPermission(requestPermission: Array<String>?): Permission?
}
