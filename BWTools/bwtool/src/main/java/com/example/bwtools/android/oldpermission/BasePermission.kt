package com.example.bwtools.android.oldpermission

import android.app.Activity

/**
 * 날짜 : 2019-09-23
 * 작성자 : 배우람
 * 기능 : Permission 기본 필요 요소
 */
abstract class BasePermission : Permission.Target,
    Permission.Request,
    Permission {
    protected var requestPermission: Array<String>? = null
    protected var targetActivity: Activity? = null

    override fun target(targetActivity: Activity?): Permission.Request? {
        this.targetActivity = targetActivity
        return this
    }

    override fun requestPermission(requestPermission: Array<String>?): Permission? {
        this.requestPermission = requestPermission
        return this
    }

}