package com.example.bwtools.android.listener

import java.lang.Exception

interface TaskListener<R> {
    fun onSuccess(result:R)
    fun onFailure(e:Exception)
}