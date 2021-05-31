package com.example.bwtools.android.permission

import android.app.Activity

interface Permission {
    interface Target {
        fun target(targetActivity: Activity?): Request?
    }

    interface Request {
        fun requestPermission(requestPermission: Array<String>?): Permission?
    }

    fun execute()
}